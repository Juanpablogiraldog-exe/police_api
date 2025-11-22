# Archivos de Datos - Police API

Este directorio contiene archivos CSV con datos de ejemplo para la API de Gestión Policial. Los datos están estructurados para usar referencias de objetos en lugar de UUIDs simples.

## Estructura de Archivos

### police_units.csv
Unidades policiales con sus miembros asociados.

**Columnas:**
- `id` - UUID único de la unidad
- `name` - Nombre de la unidad
- `members` - UUIDs de empleados separados por punto y coma (;)
- `active` - Estado de la unidad (true/false)

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440001,Unidad de Patrullaje Norte,550e8400-e29b-41d4-a716-446655440101;550e8400-e29b-41d4-a716-446655440102,true
```

### employees.csv
Empleados de las unidades policiales.

**Columnas:**
- `id` - UUID único del empleado
- `firstName` - Nombre del empleado
- `lastName` - Apellido del empleado
- `hiredDate` - Fecha de contratación (YYYY-MM-DD)
- `unit` - UUID de la unidad a la que pertenece

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440101,Juan,García,2020-05-15,550e8400-e29b-41d4-a716-446655440001
```

### cases.csv
Casos policiales con sus reportes y evidencias asociadas.

**Columnas:**
- `id` - UUID único del caso
- `title` - Título del caso
- `description` - Descripción del caso
- `crimeType` - Tipo de crimen (THEFT, ROBBERY, FRAUD, etc.)
- `assignedUnit` - UUID de la unidad asignada (puede estar vacío)
- `reports` - UUID del reporte de incidente (puede estar vacío)
- `evidences` - UUIDs de evidencias separadas por punto y coma (;)

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440201,Robo en tienda,Robo de mercancía,THEFT,550e8400-e29b-41d4-a716-446655440001,550e8400-e29b-41d4-a716-446655440301,550e8400-e29b-41d4-a716-446655440401
```

### evidences.csv
Evidencias recolectadas en los casos.

**Columnas:**
- `id` - UUID único de la evidencia
- `case_` - UUID del caso al que pertenece
- `type` - Tipo de evidencia (Fotografía, Video, Objeto, Documento, etc.)
- `description` - Descripción de la evidencia
- `collectedAt` - Fecha y hora de recolección (ISO 8601)

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440401,550e8400-e29b-41d4-a716-446655440201,Fotografía,Foto de la escena del crimen,2025-11-15T10:30:00
```

### incident_reports.csv
Reportes de incidentes asociados a casos.

**Columnas:**
- `id` - UUID único del reporte
- `case_` - UUID del caso al que pertenece
- `reporterName` - Nombre de quien reporta
- `details` - Detalles del incidente
- `reportedAt` - Fecha y hora del reporte (ISO 8601)

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440301,550e8400-e29b-41d4-a716-446655440201,Juan Pérez,Se reporta robo de mercancía,2025-11-15T09:00:00
```

### case_reports.csv
Reportes de casos (resúmenes y análisis).

**Columnas:**
- `id` - UUID único del reporte
- `case_` - UUID del caso al que pertenece
- `summary` - Resumen del caso
- `createdAt` - Fecha de creación (YYYY-MM-DD)

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440501,550e8400-e29b-41d4-a716-446655440201,Resumen del caso de robo,2025-11-15
```

### police_vehicles.csv
Vehículos policiales asignados a unidades.

**Columnas:**
- `id` - UUID único del vehículo
- `plateNumber` - Número de placa
- `model` - Modelo del vehículo
- `assignedUnit` - UUID de la unidad asignada

**Ejemplo:**
```
550e8400-e29b-41d4-a716-446655440601,POL-001,Ford Police Interceptor,550e8400-e29b-41d4-a716-446655440001
```

### case_categories.csv
Categorías de casos (sin UUID, solo nombre y descripción).

**Columnas:**
- `name` - Nombre de la categoría
- `description` - Descripción de la categoría

**Ejemplo:**
```
Robo,Casos relacionados con robo de bienes
```

## Notas Importantes

1. **Separadores de listas**: Cuando una columna contiene múltiples referencias (como `members` o `evidences`), se separan con punto y coma (`;`)

2. **Campos vacíos**: Los campos que pueden estar vacíos (como `assignedUnit` en casos sin asignar) se dejan en blanco

3. **Formatos de fecha**:
   - Fechas simples: `YYYY-MM-DD`
   - Fechas con hora: `YYYY-MM-DDTHH:MM:SS` (ISO 8601)

4. **UUIDs**: Todos los IDs son UUIDs v4 en formato estándar

5. **Relaciones**: Los datos mantienen integridad referencial mediante UUIDs que coinciden entre archivos

## Carga de Datos

Para cargar estos datos en la aplicación, se puede:

1. **Manualmente**: Usar los endpoints POST de Postman para crear registros
2. **Automáticamente**: Implementar un servicio de carga de datos al iniciar la aplicación
3. **CSV Import**: Usar un endpoint específico para importar archivos CSV

## Ejemplo de Relaciones

```
Police Unit (550e8400-e29b-41d4-a716-446655440001)
├── Employee 1 (550e8400-e29b-41d4-a716-446655440101)
├── Employee 2 (550e8400-e29b-41d4-a716-446655440102)
└── Employee 3 (550e8400-e29b-41d4-a716-446655440103)

Case (550e8400-e29b-41d4-a716-446655440201)
├── Assigned Unit: 550e8400-e29b-41d4-a716-446655440001
├── Incident Report: 550e8400-e29b-41d4-a716-446655440301
└── Evidence 1: 550e8400-e29b-41d4-a716-446655440401
```
