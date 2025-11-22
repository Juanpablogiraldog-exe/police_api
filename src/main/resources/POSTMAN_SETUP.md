# Configuración de Postman - Police API

## Importar la Colección

1. Abre Postman
2. Haz clic en **Import** (botón en la esquina superior izquierda)
3. Selecciona la pestaña **File**
4. Busca y selecciona el archivo `police_api_postman_collection.json`
5. Haz clic en **Import**

## Variables de Entorno

La colección incluye variables predefinidas que puedes personalizar:

- `caseId` - UUID de un caso
- `evidenceId` - UUID de una evidencia
- `incidentReportId` - UUID de un reporte de incidente
- `caseReportId` - UUID de un reporte de caso
- `policeUnitId` - UUID de una unidad policial
- `vehicleId` - UUID de un vehículo

Para cambiar estas variables:
1. Haz clic en el ícono de **Environment** (arriba a la derecha)
2. Selecciona **Globals** o crea un nuevo environment
3. Modifica los valores según necesites

## Estructura de la Colección

### Cases
- GET /cases - Obtener todos los casos
- GET /cases/{id} - Obtener un caso por ID
- POST /cases - Crear un nuevo caso
- PUT /cases/{id} - Actualizar un caso
- DELETE /cases/{id} - Eliminar un caso

### Evidence
- GET /evidences - Obtener todas las evidencias
- GET /evidences/{id} - Obtener una evidencia por ID
- POST /evidences - Crear una nueva evidencia
- PUT /evidences/{id} - Actualizar una evidencia
- DELETE /evidences/{id} - Eliminar una evidencia

### Incident Reports
- GET /incidentreports - Obtener todos los reportes de incidente
- GET /incidentreports/{id} - Obtener un reporte por ID
- POST /incidentreports - Crear un nuevo reporte
- PUT /incidentreports/{id} - Actualizar un reporte
- DELETE /incidentreports/{id} - Eliminar un reporte

### Case Reports
- GET /casereports - Obtener todos los reportes de caso
- GET /casereports/{id} - Obtener un reporte por ID
- POST /casereports - Crear un nuevo reporte
- PUT /casereports/{id} - Actualizar un reporte
- DELETE /casereports/{id} - Eliminar un reporte

### Police Units
- GET /policeunits - Obtener todas las unidades policiales
- GET /policeunits/{id} - Obtener una unidad por ID
- POST /policeunits - Crear una nueva unidad (con miembros)
- PUT /policeunits/{id} - Actualizar una unidad
- DELETE /policeunits/{id} - Eliminar una unidad

### Police Vehicles
- GET /policevehicles - Obtener todos los vehículos
- GET /policevehicles/{id} - Obtener un vehículo por ID
- POST /policevehicles - Crear un nuevo vehículo
- PUT /policevehicles/{id} - Actualizar un vehículo
- DELETE /policevehicles/{id} - Eliminar un vehículo

## Ejemplos de Uso

### Crear una Unidad Policial con Empleados

```json
{
  "name": "Unidad de Patrullaje Norte",
  "active": true,
  "members": [
    {
      "type": "Officer",
      "firstName": "Juan",
      "lastName": "García",
      "hiredDate": "2020-05-15",
      "badge": "OP-001",
      "rank": "Sargento",
      "unit": null
    },
    {
      "type": "Detective",
      "firstName": "María",
      "lastName": "López",
      "hiredDate": "2021-03-20",
      "specialization": "Homicidios",
      "casesResolved": 15,
      "unit": null
    }
  ]
}
```

### Crear un Caso

```json
{
  "title": "Robo en tienda",
  "description": "Robo de mercancía en tienda del centro",
  "crimeType": "THEFT",
  "assignedUnit": null,
  "reports": [],
  "evidences": []
}
```

### Crear una Evidencia

```json
{
  "case_": null,
  "type": "Fotografía",
  "description": "Foto de la escena del crimen",
  "collectedAt": "2025-11-15T10:30:00"
}
```

## Tipos de Empleados

### Officer (Oficial)
```json
{
  "type": "Officer",
  "firstName": "nombre",
  "lastName": "apellido",
  "hiredDate": "YYYY-MM-DD",
  "badge": "código de placa",
  "rank": "rango"
}
```

### Detective
```json
{
  "type": "Detective",
  "firstName": "nombre",
  "lastName": "apellido",
  "hiredDate": "YYYY-MM-DD",
  "specialization": "especialidad",
  "casesResolved": número
}
```

## Notas Importantes

- La API se ejecuta en `http://localhost:8080`
- Todos los IDs son UUIDs (UUID v4)
- Las fechas deben estar en formato ISO 8601
- Los empleados deben especificar su `type` (Officer o Detective) para la deserialización correcta
- Las relaciones entre entidades se manejan mediante referencias de objetos
