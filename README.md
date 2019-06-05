#Trabajo Práctico Obligatorio Nº 2

Esta es una práctica de entrega individual. El código fuente se entrega en un proyecto eclipse zipeado.

Implemente en java el programa grep que imprime líneas que coinciden con un patrón.
Uso
    java udc.lap.grep.Grep [opciones] patron [archivos]

Si recibe un único archivo debe imprimir sólo las líneas coincidentes; si recibe más de un archivo debe anteponer el nombre del archivo seguido de dos puntos a cada línea coincidente, si no recibe archivos debe leer de la entrada estándar.
Debe tomarse como patrón a la secuencia de caracteres ingresada, no es necesario implementar la búsqueda de expresiones regulares.
Opciones:

	-i, --ignore-case

ignora las diferencias entre mayúsculas y minúsculas tanto en el patrón como en los archivos de entrada
    
	-ie [ENCODING], --input-encoding [ENCODING]

Encoding del flujo de entrada