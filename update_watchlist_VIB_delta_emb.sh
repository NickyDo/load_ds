#. /home/emb/.bashrc
. /home/emb/.bash_profile

DATE=$(date '+%Y%m%d_%X')


if [ -s "$MAIL" ]           # This is at Shell startup.  In normal
then echo "$MAILMSG"        # operation, the Shell checks
fi                          # periodically.
echo $DATE
#echo "START LOAD VNVX DJVA......" >>/u01/BLTZ/log/djvnvx.log

$KYC_ROOT/system/watchlist/batch/start_update_WorldCheck.sh VNVX output.csv $KYC_ROOT/custom/tool/wckall_emb.properties -charsetUTF-8 -verbose -showwarning -charset UTF8 -deltalist
#KYC_ROOT/system/watchlist/batch/start_update_WorldCheck.sh VNVX dj.xml $KYC_ROOT/custom/tool/factiva_va.properties -charsetUTF-8

#echo "END LOAD VNVX DJVA......" >>/u01/BLTZ/log/djvnvx.log

#echo "START LOAD VNVX CTRY......" >>/u01/BLTZ/log/djvnvx.log
 
#$EMB_ROOT/system/watchlist/batch/start_update_Factiva.sh VNVX dj.xml $EMB_ROOT/custom/tool/factiva_ctry.properties "-verbose -showwarning -charset UTF-8" ref.xml

#echo "END LOAD VNVX CTRY......" >>/u01/BLTZ/log/djvnvx.log

#echo "START LOAD VNVX WL......" >>/u01/BLTZ/log/djvnvx.log

#$EMB_ROOT/system/watchlist/batch/start_update_WorldCheck.sh VNVX dj.xml $EMB_ROOT/custom/tool/factiva_wl.properties "-verbose -showwarning -charset UTF-8" ref.xml


echo "END LOAD VNVX WL......" >>/u01/BLTZ/log/djvnvx.log

echo "START CREATE INDICES VNVX......" >>/u01/BLTZ/log/djvnvx.log

#$EMB_ROOT/system/watchlist/batch/start_create_Indices.sh VNVX

#$EMB_ROOT/system/scoring/batch/system/stopClientTrx.sh VNVX

#$EMB_ROOT/system/scoring/batch/system/startClientTrx.sh VNVX

echo "END CREATE INDICES VNVX......" >>/u01/BLTZ/log/djvnvx.log

