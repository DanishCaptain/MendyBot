#!/bin/sh


while true; do
  java -DTEST-MODE -jar target/lib/console.jar AZ-Console-1

if [ $? -eq 5 ]; then
  echo " ............. Restarting ............. "
else
  echo " ............. Shutdown ............. "
  exit 1;
fi

done

