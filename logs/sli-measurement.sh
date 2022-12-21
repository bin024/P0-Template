#Latency
httpLatencies=$(grep Response ./rollingFile.log | cut -f 6 -d ' ')
latenciesTotal=0
numOfEntry=0
 
for num in $httpLatencies
do
	latencyTotal=$(awk "BEGIN {print $latencyTotal + $num; exist}")
	((numOfEntry++))
done



avgLatency=$(awk "BEGIN {print $latencyTotal / $numOfEntry; exist}")

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
