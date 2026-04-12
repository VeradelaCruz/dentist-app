#!/usr/bin/env pwsh
# Script para insertar datos de prueba en MongoDB de forma automática
# Ejecutar: .\run-test-data.ps1

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "Inserción de Datos de Prueba en MongoDB" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar que mongosh está disponible
try {
    $mongosh = Get-Command mongosh -ErrorAction Stop
    Write-Host "✅ mongosh encontrado en: $($mongosh.Source)" -ForegroundColor Green
} catch {
    Write-Host "❌ mongosh no está instalado o no está en el PATH" -ForegroundColor Red
    Write-Host "Descárgalo desde: https://www.mongodb.com/try/download/shell" -ForegroundColor Yellow
    exit 1
}

# Definir rutas de los scripts
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$scripts = @(
    "01-insert-dentists.mongodb",
    "02-insert-patients.mongodb",
    "03-insert-appointments.mongodb",
    "04-insert-treatments.mongodb",
    "05-insert-bills.mongodb"
)

# Función para ejecutar un script
function Execute-MongoScript {
    param(
        [string]$scriptName,
        [int]$stepNumber
    )

    $scriptFullPath = Join-Path $scriptPath $scriptName

    if (-Not (Test-Path $scriptFullPath)) {
        Write-Host "❌ Paso $stepNumber - $scriptName: NO ENCONTRADO" -ForegroundColor Red
        return $false
    }

    Write-Host "⏳ Paso $stepNumber - Ejecutando: $scriptName" -ForegroundColor Yellow

    try {
        # Ejecutar el script de MongoDB
        mongosh < $scriptFullPath

        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Paso $stepNumber - $scriptName: COMPLETADO" -ForegroundColor Green
            return $true
        } else {
            Write-Host "❌ Paso $stepNumber - $scriptName: ERROR (código: $LASTEXITCODE)" -ForegroundColor Red
            return $false
        }
    } catch {
        Write-Host "❌ Paso $stepNumber - $scriptName: EXCEPCIÓN - $_" -ForegroundColor Red
        return $false
    }
}

# Ejecutar todos los scripts en orden
$successful = 0
$failed = 0
$step = 1

Write-Host "Iniciando inserción de datos..." -ForegroundColor Cyan
Write-Host ""

foreach ($script in $scripts) {
    if (Execute-MongoScript $script $step) {
        $successful++
    } else {
        $failed++
    }
    Write-Host ""
    $step++
}

# Resumen final
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "RESUMEN DE INSERCIÓN" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "Scripts exitosos: $successful de $($scripts.Count)" -ForegroundColor Green
if ($failed -gt 0) {
    Write-Host "Scripts fallidos: $failed" -ForegroundColor Red
}
Write-Host ""

# Consultas de verificación
if ($failed -eq 0) {
    Write-Host "Verificando datos insertados..." -ForegroundColor Cyan

    $verifyScript = @"
use doctor_db;
console.log("📊 CONTEO DE DOCUMENTOS:");
console.log("  - Dentistas: " + db.dentists.countDocuments());
console.log("  - Pacientes: " + db.patients.countDocuments());
console.log("  - Citas: " + db.appointments.countDocuments());
console.log("  - Tratamientos: " + db.treatments.countDocuments());
console.log("  - Facturas: " + db.bills.countDocuments());
"@

    Write-Host $verifyScript
    $verifyScript | mongosh
}

Write-Host ""
Write-Host "Para más información, consulta: README-DATOS-PRUEBA.md" -ForegroundColor Cyan
Write-Host ""

