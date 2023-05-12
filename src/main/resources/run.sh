#!/bin/bash
_script="$(readlink -f ${BASH_SOURCE[0]})"
_base="$(dirname $_script)"

fecha="000000000000"
modo="CAMINOIDA"

if [ $# -eq 2 ]; then
    	modo="$1"
    	fecha="$2"
else
	echo "Argumentos erróneos ->  1 arg -> CAMINOIDA/CAMINOVUELTA , 2 arg -> date(yyyymmddhhmm)"
	exit 1
fi


echo "Arrancando aplicación con la siguiente configuración"

cat << EOF
spark-submit \
	--class com.redes.Main \
    	--properties-file properties.conf \
    	--master yarn \
    	--deploy-mode cluster \
     	spark_PKDDataLake-0.1-SNAPSHOT.jar $modo $fecha
EOF

cat properties.conf

spark-submit \
	--class com.redes.Main \
    	--properties-file properties.conf \
    	--master yarn \
    	--deploy-mode cluster \
     	spark_PKDDataLake-0.1-SNAPSHOT.jar $modo $fecha



RESULT=$?
if [ ${RESULT} -ne 0 ]; then
    echo "Error en la llamada al script de spark: $RESULT"
    exit $RESULT
fi
echo "Finished ok !"
exit 0
