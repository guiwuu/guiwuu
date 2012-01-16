#/bin/bash
[ -z $1 -o ! $# -eq 1 ] && echo -e "Usage:\n\t$(basename $0) appname\n"
echo $(curl http://api.pubfree.taobao.org:9999/pubhost/hosts/app/?appname=$1 2>/dev/null) | sed 's/},/\n/g' | grep -w "\"$1\"" | sed 's/,/\n/g' | sed -e 's/[" ]//g' | grep prepubhostname | awk -F: '{print $2}'
