#Latency
httpLatencies=$(grep Response ./rollingFile.log | cut -f 6 -d ' ')
totalLatency=0
numOfEntry=0
 
for num in $httpLatencies
do
	totalLatency=$(awk "BEGIN {print $totalLatency + $num; exist}")
	((numOfEntry++))
done



avgLatency=$(awk "BEGIN {print $totalLatency / $numOfEntry; exist}")

echo "HTTP average latency : $avgLatency ms"


#Error Rate
httpCodes=$(grep Response ./rollingFile.log | cut -f 2 -d ' ' | grep -oe '\([0-9.]*\)')
httpRequestTotal=0
httpFailures=0


for code in $httpCodes
do
        ((httpRequestTotal++))
        if [ $code -eq 500 ]
        then
                ((httpFailures++))
        fi
done

result=$(awk "BEGIN {print $httpFailures / $httpRequestTotal * 100; exist}")

echo "HTTP error rate: $result%"
