# Proyecto-Colas-con-rabbit

# Procesamiento de Transacciones Bancarias con RabbitMQ

## Descripción General

Sistema distribuido que procesa transacciones bancarias. Obtiene lotes de transacciones desde una API, las organiza por banco destino usando RabbitMQ, y las envía a otra API para su almacenamiento.

##  Arquitectura del Sistema

API GET
transacciones

PRODUCER   
(Componente A)

RabbitMQ   
Colas por banco

CONSUMER   
(Componente B)

API POST  
guardarTransaccion

##  Componentes

### 1. Productor (Componente A)
Función: Obtiene transacciones del API GET y las publica en RabbitMQ.
Comportamiento: Por cada transacción, la envía a una cola con el nombre del banco destino.
Tecnologías: Java, Maven, RabbitMQ, HttpClient.

### 2. RabbitMQ (Broker de mensajes)
Función: Contiene colas dinámicas, una por cada banco destino.
Bancos: BAC, BANRURAL, BI, GYT.
Garantía: Los mensajes persisten hasta ser confirmados por el consumidor.

### 3. Consumidor (Componente B)
Función:Escucha las colas de RabbitMQ, procesa cada transacción y la envía al API POST.
Confirmación: Usa ACK manual. Solo confirma si el POST fue exitoso.
Reintentos: Si falla, el mensaje vuelve a la cola.

### 4. APIs Externas (proporcionadas por el ingeniero)
GET /transacciones: Entrega lotes de transacciones simuladas.
POST /guardarTransaccion: Almacena cada transacción individual.

## Flujo de Datos:

1. El productor consulta el API GET y obtiene un lote de transacciones.
2. Por cada transacción, lee el campo bancoDestino y la publica en la cola correspondiente de RabbitMQ.
3. El consumidor escucha todas las colas simultáneamente.
4. Al recibir un mensaje, lo convierte a objeto Java y lo envía al API POST.
5. Si la respuesta del POST es exitosa, el consumidor confirma el mensaje (ACK).
6. Si falla, el mensaje se reencola para un nuevo intento.

## Garantías del Sistema:

No pérdida de mensajes: ACK manual + persistencia en RabbitMQ.
Distribución automática: Cada banco tiene su propia cola.
Tolerancia a fallos: Reintentos automáticos si el POST falla.
Desacoplamiento:Productor y consumidor no se conocen entre sí.

## Tecnologías Principales:

- Java 17
- Maven
- RabbitMQ
- Jackson (JSON)
- HttpClient (Java 11+)

## Ejecución del Sistema:

1. Iniciar RabbitMQ localmente.
2. Ejecutar el productor (obtiene y envía transacciones).
3. Ejecutar el consumidor (queda escuchando colas).
4. Verificar en consola el flujo de mensajes.

## Identificación en Transacciones:

Cada transacción incluye los siguientes campos adicionales:
- nombre: Jose Rosales
- carnet: 0905-24-17488
- idTransaccion: Prefijo JoseMario- + UUID

##  Cumplimiento de Requisitos:

Requisitos:
Distribución por banco
Procesamiento independiente
Garantía de no pérdida
Manejo de errores 
ACK manual 
Colas dinámicas

## Principios SOLID Aplicados:

### S — Single Responsibility Principle (Responsabilidad Única):

Se separó la lógica de conexión a RabbitMQ en una clase independiente para que ConsumerService se enfoque únicamente en consumir mensajes.

Clase creada:
RabbitMQConnection.java: Maneja exclusivamente la conexión con RabbitMQ.

Beneficio:
Si cambia la configuración de RabbitMQ, solo se modifica esta clase.
ConsumerService queda más limpio y enfocado en su responsabilidad.

### O — Open/Closed Principle (Abierto/Cerrado):

La lista de bancos se movió a un archivo de propiedades externo, permitiendo agregar nuevos bancos sin modificar el código fuente.

Archivos creados:
config.properties (en src/main/resources/): Contiene los bancos soportados.
Config.java: Lee las propiedades desde el archivo.

Beneficio:
Para agregar el banco "INDUSTRIAL", solo se edita config.properties.
El código queda cerrado a modificaciones pero abierto a extensiones.

## Video Demostracion:
https://drive.google.com/file/d/1KO5wAi6O9ZELuXu2c8xmifyY4HXIl85T/view?usp=drivesdk
