echo "==================BEGIN DEPLOY START=================="

source config.properties

isLinux=false
if [ $(uname -o | grep "Linux") != "" ];then
  isLinux=true
else
  isLinux=false
fi

echo "isLinux : $isLinux"
echo "EDGENAME : $EDGENAME"
echo "NODENAME : $NODENAME"
echo "EVN : $EVN"
echo "KEYCLOAKURL : $KEYCLOAKURL"
echo "EDGEIP : $EDGEIP"
echo "TEMPOUTPUTDIR : $TEMPOUTPUTDIR"

if [ ! -d $TEMPOUTPUTDIR ];then
  mkdir -p $TEMPOUTPUTDIR
  echo "mkidr "  $TEMPOUTPUTDIR
else
  echo $TEMPOUTPUTDIR "exit and rm "
  rm -rf $TEMPOUTPUTDIR/*
fi

cp -r temp/  $TEMPOUTPUTDIR/

echo "cp temp files to $TEMPOUTPUTDIR "

echo "begin reprace variable"

if [ $isLinux == true ];then
  sed -i s/#EDGENAME#/$EDGENAME/g" `grep "#EDGENAME#" -rl $TEMPOUTPUTDIR/`
  sed -i s/#NODENAME#/$NODENAME/g" `grep "#NODENAME#" -rl $TEMPOUTPUTDIR/`
  sed -i s/#EVN#/$EVN/g" `grep "#EVN#" -rl $TEMPOUTPUTDIR/`
  sed -i s/#KEYCLOAKURL#/$KEYCLOAKURL/g" `grep "#KEYCLOAKURL#" -rl $TEMPOUTPUTDIR/`
else
  sed -i "" "s/#EDGENAME#/$EDGENAME/g" `grep "#EDGENAME#" -rl $TEMPOUTPUTDIR/`
  sed -i "" "s/#NODENAME#/$NODENAME/g" `grep "#NODENAME#" -rl $TEMPOUTPUTDIR/`
  sed -i "" "s/#EVN#/$EVN/g" `grep "#EVN#" -rl $TEMPOUTPUTDIR/`
  sed -i "" "s/#KEYCLOAKURL#/$KEYCLOAKURL/g" `grep "#KEYCLOAKURL#" -rl $TEMPOUTPUTDIR/`
fi

echo "end reprace variable"

kubectl apply -f  $TEMPOUTPUTDIR/*