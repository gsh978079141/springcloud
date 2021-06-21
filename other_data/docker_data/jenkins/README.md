docker run -d -v ./data:/home/vsftpd -p 21:21 -p 21100-21110:21100-21110 -e FTP_USER=gsh -e FTP_PASS=gsh -e PASV_ADDRESS=192.168.103.145 -e FILE_OPEN_MODE=0755 -e LOCAL_UMASK=022 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 -e PASV_ADDRESS_ENABLE=true --name ftp-server --restart=always fauria/vsftpd




docker run -d -v /d/WorkSpaces/Github_Projects/springcloud/other_data/docker_data/ftp/data:/home/vsftpd -p 21:21 -p 21100-21110:21100-21110 -e FTP_USER=gsh -e FTP_PASS=gsh -e PASV_ADDRESS=192.168.103.145 -e FILE_OPEN_MODE=0755 -e LOCAL_UMASK=022 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 -e PASV_ADDRESS_ENABLE=true --name ftp-server --restart=always fauria/vsftpd