httpLatencies=$(grep Response ./rollingFile.log | cut -f 2 -d : | cut -f 4 -d , | cut -f 4 -d ' ')
totalLatency=0
numOfEntry=0
 
for latency in $httpLatencies
do
	totalLatency=$(echo "sacle=3; $totalLatency + $latency" | bc )
	((numOfEntry++))
done

avgLatency=$(echo "scale=3; $totalLatency / $numOfEntry" | bc)
echo $avgLatency

echo "$httpStatusCodes"
