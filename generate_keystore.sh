#!/bin/sh

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 keystore_name alias" >&2
  exit 1
fi

function makepass() {
	local myresult=$(openssl rand -base64 32)
	echo "$myresult"
}

KEYALGORITHM=RSA
KEYSIZE=2048
VALIDITY=10000

KEYSTORE_NAME=$1
KEYSTORE_ALIAS=$2
STOREPASS=$(makepass) 
KEYPASS=$(makepass) 


keytool -genkey -v -keystore $KEYSTORE_NAME -alias $KEYSTORE_ALIAS -keyalg $KEYALGORITHM -keysize $KEYSIZE -validity $VALIDITY -storepass $STOREPASS -keypass $KEYPASS

echo ""
echo "------------------------------"
echo "storeFile: " $KEYSTORE_NAME
echo "storePassword: " $STOREPASS
echo "keyAlias : " $KEYSTORE_ALIAS
echo "keyPassword: " $KEYPASS