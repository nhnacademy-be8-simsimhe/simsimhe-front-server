  echo "Removing port {PORT} from load balancer"
          sed -i '/localhost:{PORT}/d' /etc/nginx/nginx.conf
          sudo nginx -t
          sudo systemctl reload nginx


sudo fuser -k {PORT}/tcp

nohup /usr/local/java/java21/bin/java -Dserver.port={PORT} -jar ~/target/*SNAPSHOT.jar > log 2>&1 &


 echo "Adding port {PORT} back to load balancer"
          sed -i '/upstream loadbalancer {/a \    server localhost:{PORT};' /etc/nginx/nginx.conf
          sudo nginx -t
          sudo systemctl reload nginx
