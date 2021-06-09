yum install nfs-utils rpcbind -y

systemctl start nfs
systemctl start rpcbind
systemctl enable nfs
systemctl enable rpcbind

mkdir -p /opt/nfs

chmod 777 /opt/nfs