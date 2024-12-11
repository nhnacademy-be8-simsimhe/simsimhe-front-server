current_port = {PORT}
ip="127.0.0.1"


echo -e "http://$ip:$current_port/management/health_check"
RESPONSE=$(curl -s http://$ip:$current_port/management/health_check)
IS_ACTIVE=$(echo ${RESPONSE} | grep 'UP' | wc -l)
if [ $IS_ACTIVE -eq 1 ];
then
  echo -e "nginx 설정파일에서 $current_port를 제거합니다."
  sudo sed -i "/127.0.0.1:$current_port/d" /etc/nginx/nginx.conf

  sudo nginx -t
  echo "nginx를 reload합니다."
  sudo nginx -s reload

  fuser -s -k -TERM $current_port/tcp

  echo -e "jar파일을 $port포트에 실행합니다."
  nohup java -jar -Dserver.port=${current_port} ~/target/gitAction_practice-0.0.1-SNAPSHOT.jar > log 2>&1 &

  for retry in {1..10}
  do
    RESPONSE=$(curl -s http://$ip:$current_port/management/health_check)
    PORT_HEALTH=$(echo ${RESPONSE} | grep 'UP' | wc -l)
    if [ $PORT_HEALTH -eq 1 ];
    then
      break
    else
      echo -e "$ip:$current_port가 켜져있지 않습니다. 10초 슬립하고 다시 헬스체크를 수행합니다."
      sleep 10
    fi
  done

  if [ $PORT_HEALTH -eq 1 ];
  then
    echo -e "$ip:$current_port에 정상적으로 spring boot가 실행 중입니다."
  else
    echo -e "$ip:$current_port에 정상적으로 spring boot가 실행 중이 아닙니다."
    exit 0
  fi

  echo -e "nginx 설정파일에 $ip:$current_port을 추가합니다."
  sudo sed -i "/upstream servers {/ a \    server 127.0.0.1:$current_port;" /etc/nginx/nginx.conf

  sudo nginx -t
  echo "nginx를 reload합니다."
  sudo nginx -s reload
else
  echo -e "$current_port포트에 spring boot가 실행중이 아닙니다."
  exit 0
fi




