export CLASSPATH=~/Downloads/bcprov-jdk15on-157.jar
CERTSTORE=res/raw/mystore.bks
if [ -a $CERTSTORE ]; then
    rm $CERTSTORE || exit 1
fi
keytool \
      -import \
      -v \
      -trustcacerts \
      -alias 0 \
      -file <(openssl x509 -in mycert.pem) \
      -keystore $CERTSTORE \
      -storetype BKS \
      -provider org.bouncycastle.jce.provider.BouncyCastleProvider \
      -providerpath /path/to/bouncycastle/bcprov-jdk15on-155.jar \
      -storepass some-password
