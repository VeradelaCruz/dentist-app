#!/bin/bash
# Script para insertar datos de prueba en MongoDB de forma automática
# Ejecutar: bash run-test-data.sh

echo "================================================"
echo "Inserción de Datos de Prueba en MongoDB"
echo "================================================"
echo ""

# Verificar que mongosh está disponible
if ! command -v mongosh &> /dev/null; then
    echo "❌ mongosh no está instalado o no está en el PATH"
    echo "Descárgalo desde: https://www.mongodb.com/try/download/shell"
    exit 1
fi

echo "✅ mongosh encontrado"
echo ""

# Definir rutas de los scripts
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SCRIPTS=(
    "01-insert-dentists.mongodb"
    "02-insert-patients.mongodb"
    "03-insert-appointments.mongodb"
    "04-insert-treatments.mongodb"
    "05-insert-bills.mongodb"
)

# Función para ejecutar un script
execute_mongo_script() {
    local script_name=$1
    local step_number=$2
    local script_full_path="$SCRIPT_DIR/$script_name"

    if [ ! -f "$script_full_path" ]; then
        echo "❌ Paso $step_number - $script_name: NO ENCONTRADO"
        return 1
    fi

    echo "⏳ Paso $step_number - Ejecutando: $script_name"

    mongosh < "$script_full_path"

    if [ $? -eq 0 ]; then
        echo "✅ Paso $step_number - $script_name: COMPLETADO"
        return 0
    else
        echo "❌ Paso $step_number - $script_name: ERROR"
        return 1
    fi
}

# Ejecutar todos los scripts en orden
SUCCESSFUL=0
FAILED=0
STEP=1

echo "Iniciando inserción de datos..."
echo ""

for script in "${SCRIPTS[@]}"; do
    if execute_mongo_script "$script" "$STEP"; then
        ((SUCCESSFUL++))
    else
        ((FAILED++))
    fi
    echo ""
    ((STEP++))
done

# Resumen final
echo "================================================"
echo "RESUMEN DE INSERCIÓN"
echo "================================================"
echo "Scripts exitosos: $SUCCESSFUL de ${#SCRIPTS[@]}"
if [ $FAILED -gt 0 ]; then
    echo "Scripts fallidos: $FAILED"
fi
echo ""

# Consultas de verificación
if [ $FAILED -eq 0 ]; then
    echo "Verificando datos insertados..."
    echo ""

    mongosh <<EOF
use doctor_db;
console.log("📊 CONTEO DE DOCUMENTOS:");
console.log("  - Dentistas: " + db.dentists.countDocuments());
console.log("  - Pacientes: " + db.patients.countDocuments());
console.log("  - Citas: " + db.appointments.countDocuments());
console.log("  - Tratamientos: " + db.treatments.countDocuments());
console.log("  - Facturas: " + db.bills.countDocuments());
EOF
fi

echo ""
echo "Para más información, consulta: README-DATOS-PRUEBA.md"
echo ""

