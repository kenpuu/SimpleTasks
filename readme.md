# SimpleTasks

Simpletask es un pequeño gestor de tareas colaborativo, desarrollado como proyecto final del curso de Android impartido por los GDG de España.

Usa una API creada con django-rest-framework, que permite crear, eliminar y comentar tareas, así como cambiarlas de estado(a elegir entre distintos estados predefinidos), y añadir imágenes a esas tareas. Esta API se ha implementado co el objetivo únicamente de usarla en la aplicación para GDG, pero no tenía mucho sentido subirla también al repositorio.

Solamente el creador de una tarea puede compartirla con más gente, pero cualquier usuario que tenga la tarea asignada tendrá todos los permisos que tiene el que la creó(esto incluye eliminarla).

Para poder usar la aplicación hay que hacer login en la ventana de preferencias. Hay creados tres usuarios de prueba, para así poder compartirse las tareas entre ellos:
  - demo1
  - demo2
  - demo3

Para los tres la contraseña es demodemo.
