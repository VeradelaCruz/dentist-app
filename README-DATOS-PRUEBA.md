# 📋 Guía de Datos de Prueba para MongoDB

## 📌 Descripción General

Esta carpeta contiene scripts para insertar datos de prueba en MongoDB para los microservicios de la aplicación dental. Los scripts están diseñados para ser ejecutados en orden específico debido a las dependencias entre colecciones.

---

## 🗂️ Estructura de Scripts

### **Orden de Ejecución (Obligatorio)**

1. **`01-insert-dentists.mongodb`** - Dentistas (sin dependencias)
   - 6 dentistas con especialidades diferentes
   - Colección: `dentists`

2. **`02-insert-patients.mongodb`** - Pacientes (sin dependencias)
   - 10 pacientes con datos completos
   - Colección: `patients`

3. **`03-insert-appointments.mongodb`** - Citas
   - 12 citas que referencia patientId y doctorId
   - Colección: `appointments`

4. **`04-insert-treatments.mongodb`** - Tratamientos
   - 8 tratamientos que referencia patientId y dentistId
   - Colección: `treatments`

5. **`05-insert-bills.mongodb`** - Facturas
   - 7 facturas que referencia appointmentId
   - Colección: `bills`

---

## 🚀 Cómo Ejecutar los Scripts

### **Opción 1: Usando MongoDB Shell (mongosh)**

```bash
# Conectarse a MongoDB localmente
mongosh

# Ejecutar cada script en orden
load("C:/Users/maida/Desktop/Proyectos/dentist-app/01-insert-dentists.mongodb")
load("C:/Users/maida/Desktop/Proyectos/dentist-app/02-insert-patients.mongodb")
load("C:/Users/maida/Desktop/Proyectos/dentist-app/03-insert-appointments.mongodb")
load("C:/Users/maida/Desktop/Proyectos/dentist-app/04-insert-treatments.mongodb")
load("C:/Users/maida/Desktop/Proyectos/dentist-app/05-insert-bills.mongodb")
```

### **Opción 2: Usando MongoDB Compass (GUI)**

1. Abre MongoDB Compass
2. Conecta a tu instancia de MongoDB (ej: `mongodb://localhost:27017`)
3. Selecciona la base de datos `doctor_db`
4. En la pestaña de terminal/shell, abre y ejecuta cada archivo en orden

### **Opción 3: Usando Docker (recomendado para desarrollo)**

Si tienes Docker, puedes usar mongo-express o una instancia Docker de MongoDB:

```bash
# Si tienes un contenedor MongoDB corriendo
docker exec -it <nombre_contenedor_mongo> mongosh < ruta/del/script.mongodb

# O copiar el script al contenedor primero
docker cp 01-insert-dentists.mongodb <nombre_contenedor>:/tmp/
docker exec -it <nombre_contenedor_mongo> mongosh < /tmp/01-insert-dentists.mongodb
```

### **Opción 4: Ejecutar todos los scripts con un batch script**

Crear un archivo `run-all-scripts.sh` (Linux/Mac) o `run-all-scripts.bat` (Windows):

**Para PowerShell (Windows):**
```powershell
# Archivo: run-all-scripts.ps1
mongosh "C:\Users\maida\Desktop\Proyectos\dentist-app\01-insert-dentists.mongodb"
mongosh "C:\Users\maida\Desktop\Proyectos\dentist-app\02-insert-patients.mongodb"
mongosh "C:\Users\maida\Desktop\Proyectos\dentist-app\03-insert-appointments.mongodb"
mongosh "C:\Users\maida\Desktop\Proyectos\dentist-app\04-insert-treatments.mongodb"
mongosh "C:\Users\maida\Desktop\Proyectos\dentist-app\05-insert-bills.mongodb"
```

---

## 📊 Datos Insertados

### **Colección: dentists** (6 registros)
- D001: María García López - Ortodoncia
- D002: Juan Martínez Rodríguez - Implantología
- D003: Carmen Fernández Sánchez - Periodoncia
- D004: Pedro López González - Endodoncia
- D005: Sofía Ruiz Domínguez - Estética Dental
- D006: Carlos Moreno Jiménez - Odontología General

### **Colección: patients** (10 registros)
- P001 a P010: Pacientes con datos completos
- Todos con email único
- Teléfonos válidos en formato +34

### **Colección: appointments** (12 registros)
- APT001 a APT012: Citas programadas y completadas
- Precios desde 80€ hasta 1500€
- Estados: SCHEDULED, COMPLETED

### **Colección: treatments** (8 registros)
- TRT001 a TRT008: Diversos tratamientos dentales
- Incluyen descripción detallada

### **Colección: bills** (7 registros)
- BILL001 a BILL007: Facturas con seguimiento de pago
- Métodos de pago: CREDIT_CARD, DEBIT_CARD, PAYPAL, CASH, INSURANCE
- Estados: PAYED, PAYMENT_PENDING

---

## 🔗 Dependencias Entre Colecciones

```
dentists (independiente)
  ↓
patients (independiente)
  ↓
appointments (referencia: patientId, doctorId)
  ↓
treatments (referencia: patientId, dentistId)
  ↓
bills (referencia: appointmentId)
```

⚠️ **Importante**: Respetar este orden para evitar referencias rotas a IDs inexistentes.

---

## 🔍 Consultas Útiles para Verificar

```javascript
// Verificar que se insertaron correctamente
use doctor_db;

// Contar registros
db.dentists.countDocuments()      // Debe retornar 6
db.patients.countDocuments()      // Debe retornar 10
db.appointments.countDocuments()  // Debe retornar 12
db.treatments.countDocuments()    // Debe retornar 8
db.bills.countDocuments()         // Debe retornar 7

// Ver un ejemplo
db.dentists.findOne()
db.patients.findOne()
db.appointments.findOne()
db.treatments.findOne()
db.bills.findOne()

// Buscar citas de un paciente específico
db.appointments.find({ patientId: "P001" })

// Ver facturas pendientes
db.bills.find({ status: "PAYMENT_PENDING" })
```

---

## ⚙️ Configuración de MongoDB

### **Base de datos**: `doctor_db`

Esta es la base de datos utilizada por todos los microservicios según sus `application.yaml`:

```yaml
spring:
  data:
    mongodb:
      uri: ${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/doctor_db}
```

### **Conexión Local**
```
mongodb://localhost:27017/doctor_db
```

### **Conexión en Docker Compose**
```
mongodb://mongodb:27017/doctor_db
```

---

## 🛠️ Limpiar Datos (si es necesario)

Para empezar de nuevo, ejecutar:

```javascript
use doctor_db;

db.dentists.deleteMany({})
db.patients.deleteMany({})
db.appointments.deleteMany({})
db.treatments.deleteMany({})
db.bills.deleteMany({})

// O eliminar toda la base de datos
db.dropDatabase()
```

---

## 📝 Notas Importantes

- **IDs Predecibles**: Los scripts usan IDs como "D001", "P001", etc., para facilitar el testing
- **MongoDB Object IDs**: El `_id` está generado automáticamente para cada documento
- **Emails Únicos**: Todos los emails son únicos, como requiere el índice de la base de datos
- **Teléfonos**: Todos están en formato +34 (España), como valida el modelo Patient
- **Fechas**: Están en formato ISO8601
- **Referencias**: Se utilizan IDs de negocio (P001, D001) en lugar de Object IDs para las referencias cruzadas

---

## ✅ Servicios Cubiertos

| Servicio | Colección | Estatus |
|----------|-----------|---------|
| patient-service | patients | ✅ Incluido |
| dentist-service | dentists | ✅ Incluido |
| appointment-service | appointments | ✅ Incluido |
| treatment-service | treatments | ✅ Incluido |
| billing-service | bills | ✅ Incluido |
| auth-service | - | ❌ Sin modelos expuestos |
| notification-service | - | ❌ Excluido por solicitud |
| api-gateway | - | ❌ No usa MongoDB |
| config-service | - | ❌ Config Server |

---

## 📞 Soporte

Si encuentras problemas:

1. Verifica que MongoDB esté corriendo: `mongosh --eval "db.version()"`
2. Verifica la conexión a la base de datos correcta: `use doctor_db; show collections`
3. Revisa que no haya restricciones de índices únicos duplicados
4. Consulta los logs de MongoDB para más detalles

---

**Última actualización**: 2025-05-05
**Versión**: 1.0

