# ¿Cómo contribuir a este proyecto?

Tabla de contenido

1.  Empezando
    
2.  Desarrollo de una tarea
    
3.  Estructura del proyecto
    
4.  Entorno de desarrollo
    
5.  Configuración de un entorno de desarrollo
    
6.  Control de versiones (Git)
    
7.  Tests
    
7.1 Tests por módulo

7.2 El módulo tests

7.3 Ejecución de los tests

7.3.1 Ejecución de los tests en Maven

7.3.2 Ejecución de los tests en Eclipse

7.4 Análisis de los resultados de los tests

8.  Guía de estilo

8.1. Código fuente
 

## 1. Empezando

El proyecto LETTA se desarrolla en un entorno de integración continua con despliegue continuo en un servidor de pre-producción (Tomcat). Este entorno está compuesto por varias herramientas que automatizan el proceso, todas ellas dirigidas por el POM de este proyecto.

En este documento encontrarás una descripción de este entorno y las instrucciones para saber cómo contribuir correctamente a este proyecto.

  

## 2. Desarrollo de una tarea

El proceso habitual para realizar una tarea será, normalmente, el siguiente:

1.  En Kunagi selecciona la tarea de la que seas responsable de la lista de ToDo que deseas desarrollar y lee bien la descripción de la misma. Reclama esa tarea para que se mueva a la columna de CLAIMED, e introduce un comentario con los nombres de la pareja de desarrolladores.
    
2.  Abre el entorno de desarrollo.
    
3.  Verifica que te encuentras en la rama development. Si no es así, cámbiate a esta rama.
    
4.  Haz pull de los últimos cambios (ver sección 6).
    
5.  Implementa la solución (ver sección 7).
    
6.  Haz un commit con cada parte estable (completa) que desarrolles.
    
7.  Cada vez que hagas un commit envíalo al repositorio central Gitlab para compartirlo con el resto del equipo (ver sección 6).
    
8.  Cuando acabes la jornada de trabajo recuerda introducir las horas en la tarea de Kunagi.

9. Cuando consideres que la tarea está finalizada, avisar al resto de miembros del equipo para su revisión.

10. Para la revisión de una tarea, han de estar implementados todos los test que la afecten, y deben superarse todos los test para poder pasarla a estado finalizado.
    

En las siguientes secciones encontrarás información que te ayudará a realizar este trabajo.

  

## 3. Estructura del proyecto

Este proyecto está estructurado en los siguientes paquetes:

-   TEST:
    

Paquetes de test que contienen utilidades para realizar los tests. Habrá test de unidad y de integración (REST)

-   ENTITIES:
    

Paquete que contiene las clases de dominio (entidades).

-   REST:
    

Paquete que contiene una capa de servicios REST.

-   DAO:
    

Paquete que contiene una capa de acceso a la base da datos con JDBC.

-   UTIL:
    

Paquete que contiene funcionalidades auxiliares que son transversales al proyecto.

  

## 4. Entorno de desarrollo

Las herramientas que componen el entorno de integración continua son las siguientes:

-   Maven 3:
    

Maven es un entorno de construcción de proyectos para Java. Se trata de una herramienta clave, ya que es quien dirigirá todo el proyecto. Para ello es necesario que tengas instalado Maven 3 en tu equipo de desarrollo para poder construir el proyecto.

-   Kunagi:
    

Es una herramienta de gestión de proyectos Scrum. En ella encontrarás toda la información sobre las funcionalidades desarrolladas y por desarrollar, el alcance de las publicaciones, el estado de desarrollo, etc. Puedes acceder a través del siguiente [enlace](https://sing-group.org/dt/kunagi/#project=C17DD358-6536-4423-827D-404CDE782AEB).

-   Git y Gitlab:
    

Git es el sistema de control de versiones que se decidió utilizar en el proyecto. Es un sistema de control de versiones distribuido que facilita la colaboración entre desarrolladores. Es necesario que tengas instalado Git en tu sistema para poder realizar cambios en el proyecto y colaborar con el resto del equipo.

Por otro lado, Gitlab es un front-end del repositorio Git común. Esta herramienta facilita la visualización de los commits y ficheros del proyecto,además de proporcionar alguna otra funcionalidad que mejora la colaboración.

-   MySQL 5+:
    

Es el sistema gestor de base de datos (SGDB) que utilizará el sistema definitivo. En la explicación de cómo ejecutar el sistema en local utilizaremos este SGBD, por lo que deberás tenerlo instalado en tu equipo.

En el siguiente diagrama se detallan las distintas secciones que componen el entorno de desarrollo, junto con las distintas tecnologías y herramientas empleadas en cada sección.

  

## 5. Configuración de un entorno de desarrollo

Empezar a trabajar en el proyecto es tan sencillo como seguir los siguientes pasos:

1.  Instala Git y Maven. Si estás en un entorno Ubuntu es tan sencillo como ejecutar sudo apt-get install git maven. También es recomendable que instales algún visor de Git como gitk o qgit.
    
2.  Clona el repositorio Git utilizando el comando:
    
```
git clone http://sing-group.org/dt/gitlab/daa2021-teamB/letta.git
```

3.  Instala Eclipse for Java EE (opcional pero recomendado):
    

4.  Descarga el IDE desde [https://www.eclipse.org/downloads/eclipse-packages/](https://www.eclipse.org/downloads/eclipse-packages/)
    
5.  Importa el proyecto en Eclipse utilizando Import...->Existing Maven projects, selecciona el directorio del proyecto en Root directory y marca todos los proyectos que aparezcan.
    

En la sección 7.2.2 aparece detallada la configuración necesaria para ejecutar los tests desde Eclipse.

Con esto ya sería suficiente para poder empezar a trabajar en el proyecto. Si, además, quieres poder ejecutarlo de forma local, deberás seguir las siguientes instrucciones.

  

### 5.1 MySQL

Cuando se ejecute la aplicación en local se utilizará una base de datos MySQL para almacenar los datos y que, de este modo, sean persistentes, tal y como ocurriría en una ejecución real. Además, aunque los tests se ejecutan por defecto utilizando una base de datos en memoria, también es posible ejecutarlos utilizando una base de datos MySQL.

Este proyecto está configurado para funcionar con una base de datos MySQL 5+.

Dependiendo de la distribución que estés utilizando, la versión de MySQL que se instalará podrá variar entre una 5+ o una 8+.

  

## 6. Control de versiones (Git)

El modelo de control de versiones que utilizaremos inicialmente será muy sencillo ya que solo utilizaremos dos ramas:

master: a esta rama solo se enviarán los commits cuando se llegue a una versión estable y publicable (una release). Estas versiones deberán estar etiquetadas con el número de versión correspondiente. Antes de hacer un merge a master el código debe de cumplir todos los test de unidad e integración.

development: esta será la rama principal de trabajo. Los commits que se envíen deben ser estables, lo que supone que todos los test implementados deben superarse exitosamente al construir la aplicación en local.

tmp-: las ramas con el prefijo tmp- son ramas temporales. Cada pareja sólo podrá tener una única rama temporal, que deberá eliminar del repositorio remoto en el momento que ya no sean necesarias. Las ramas temporales siempre deben crearse desde la rama develop. La pareja o desarrollador propietarios de la rama podrán hacer push y pull, mientras que el resto solo podrán hacer pull. Este tipo de ramas admite cualquier tipo de commit (p.ej. código incompleto, código que no compila, etc.) y, por tanto, no serán controladas por el servidor de integración continua. Por último, el nombre de la rama debe ser:tmp-*login*: dónde *login* es el identificador del usuario propietario.

Todos los commits del repositorio han de tener un título y descripción, contenida en la descripción debe de estar el nombre del desarrollador o pareja de desarrolladores que realizan el commit. Están completamente prohibidos los force push y los rebase sobre el repositorio remoto, salvo consenso de todos los miembros del equipo. No se pueden realizar commits directamente a master, los cambios tendrán que realizarse sobre la rama development y posteriormente hacer un merge a master cuando se cumplan todos los requisitos para considerarla entregada. Los commits deberán ser los más frecuentes posibles, preferiblemente uno por cada implementación de subfuncionalidad / refactorización realizada.

A la hora de hacer pull siempre es preferible hacerlo con la opción --rebase para evitar merge commits innecesarios en la historia del proyecto con el comando :

	git pull --rebase

O configurar por defecto que git realice rebase en vez de merge a la hora de hacer un pull con el comando

	git config pull.rebase true

Cabe decir que en caso de realizar un pull y darse conflictos, han de ser resueltos en local y confirmados al sistema de control de versiones mediante los commandos 
	
	git add . (o cada uno de los archivos que dan conflicto)
	git rebase --continue
  
Es posible que quieras descargar la última versión sin tener los ultimos cambios listos en un commit (por ejemplo si quieres integrar el trabajo de un compañero en el tuyo), para hacer esto sin que de error puedes hacer un stash de los cambios sin commit mediante el comando
	
	git stash
	
Y una vez te descarges la última versión puedes recuperar esos cambios mediante el comando
	
	git stash pop
	
Puede ser que de similar manera, al recuperar el stash se den conflictos. Hay que resolver estos conflictos individualmente. Si necesitas hacer otro stash después de resolver estos conflictos, es necesario hacer un git add de los archivos conflictivos para indicarle a git que ya están resueltos. Una vez hecho un stash, todos los cambios en la zona de staging se borran y el estado de los archivos queda en unstaged cuando se recuperen mediante un stash pop.

## 7. Tests

Serán necesarios los test de unidad de todas las entidades, y los test de integración de los servicios REST, todo ello utilizando JUNIT. No se realizará test de la capa de front. Solo serán testeados los constructores y los métodos con una cierta lógica, como pueden ser los métodos de las relaciones (p.ej. getOwner(), getPets(), etc.).

Como norma general, los métodos de prueba deben ser lo más sencillos posible, de modo que sea sencillo comprender qué es lo que se está evaluando. En base a esta regla, no añadiremos documentación Javadoc a los métodos de prueba.
 

### 7.1 El módulo tests

En el módulo tests se añadirán varias utilidades para realizar los tests, entre las que encontraremos, principalmente, tres tipos distintos:

* Clases Datasets: estas clases representan un conjunto de datos de pruebas. Contienen métodos para obtener a entidades que resultan de utilidad en los tests. Estas clases deben ubicarse en el mismo paquete que las entidades que contienen. El contenido de estas clases debe ser equivalente al contenido de los datasets de DBUnit que se describe a continuación.

* Datasets DBUnit: los datasets DBUnit son representaciones en forma de XML de conjuntos de datos usados en los tests y pueden ser utilizados directamente por Arquillian con las anotaciones @UsingDataSet y @ShouldMatchDataSet. El contenido de estos ficheros debe ser el equivalente al de las clases dataset. Estos ficheros deben almacenarse en el directorio src/main/resources/datasets.

* Matchers Hamcrest para entidades: cada entidad debería tener un matcher de Hamcrest que permita compararla con otras entidades. Para facilitar el desarrollo de estos matchers se incluye la clase IsEqualsToEntity, que actúa como clase base para comparar dos entidades por sus propiedades.

  

### 7.2 Ejecución de los tests

#### 7.2.1 Ejecución de los tests en Maven

Este proyecto está configurado para ejecutar, únicamente, los tests de aquellas clases cuyo nombre acabe en TestSuite. La intención es que estas clases sean suites de tests que agrupen los casos de prueba del proyecto. Por lo tanto,es importante que todos los casos de prueba que se deseen ejecutar en el proyecto estén asociados a una suite de pruebas. Todos los tests del proyecto están configurados para ser ejecutados como tests normales y no como tests de integración. Esto significa que se pueden lanzar todos simplemente ejecutando el comando:

	mvn clean install

#### 7.2.2 Ejecución de los tests en Eclipse

La ejecución de los test con Arquillian desde Eclipse, necesita de una pequeña configuración adicional en las configuraciones de ejecución para que incluyan los siguientes parámetros como propiedades del sistema:

Si, además, queremos ejecutar los tests utilizando el perfil de MySQL, debemos añadir las propiedades del sistema:

Estos parámetros pueden establecerse en el diálogo Run->Run Configurations..., donde seleccionaremos la configuración de ejecución o crearemos una nueva. En el panel de configuración de la configuración de ejecución debemos seleccionar la pestaña Arguments e introducir estos parámetros en el campo VM Arguments.

  

### 7.3 Análisis de los resultados de los tests

Cada vez que se ejecutan los tests se generarán varios ficheros con información sobre los resultados. Concretamente, se generarán informes JUnit:

Genera informes sobre el éxito o fracaso de los tests. Estos informes se almacenan en module>/target/surefire-reports. Son ficheros XML que pueden abrirse con Eclipse.

Si realizamos la ejecución desde Eclipse, la misma información que muestran los informes de JUnit nos aparecerá directamente en la vista de JUnit.

  

## 8. Guía de estilo

Un elemento importante para poder colaborar es que exista una uniformidad en el código y otros elementos que forman parte del proceso de desarrollo. Esta sección está pensada para servir como una pequeña guía de estilo que debe respetarse al trabajar en este proyecto.

  

### 8.1. Código fuente

Para uniformizar el código fuente deben respetarse las siguientes normas:
    
-   Formato de código: el código debe estar formateado, preferiblemente, siguiendo la Guía de Estilo para Java de Google o, al menos, utilizando el formato de código de Eclipse (Ctrl+Mayus+F).
    
-   Comentarios: debe evitarse completamente el código comentado y, en la medida de lo posible, los comentarios en el código.
    
-   Documentación: todas las clases deben incluir documentación Javadoc que describa las responsabilidades de la misma. No es obligatorio documentar los métodos. Se recomienda que se verifique que la documentación es correcta utilizando el comando:
    

		mvn javadoc:javadoc.

Este comando generará la documentación en formato HTML y fallará si encuentra algún error en la documentación.