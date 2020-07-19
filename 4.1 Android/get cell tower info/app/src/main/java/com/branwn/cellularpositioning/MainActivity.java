package com.branwn.cellularpositioning;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btnGetLocation = (Button) findViewById(R.id.button1);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                onBtnClick(view);
            }
        });
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    private void onBtnClick(View view) {
        Snackbar.make(view, "Loading...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        String errorText = "Unknow error";
        try {
            /** get cell tower info */

            List<CellInfo> cellInfoList = getCellInfo();

            if (cellInfoList == null){
                errorText = "Error in getCellInfo()";
            }
            /** get lat. and lng. by tower cell info, using Google's API */

            SItude itude;
            itude = getLocation.post_JSON_and_get_response(cellInfoList.get(0));



            /** show result*/

            showResult(cellInfoList , itude);


        } catch (Exception e) {

            Snackbar.make(view, errorText, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }


    private List<CellInfo>  getCellInfo() throws Exception {


        TelephonyManager mTelNet = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        int dbmLevel = -1;
        List<CellInfo> cellInfoList = mTelNet.getAllCellInfo();

        return  cellInfoList;
    }


    private void showResult(List<CellInfo> cellInfoList, SItude itude) {
        TextView cellText = (TextView) findViewById(R.id.cellText);
        cellText.setText("Cells Info");
        if (cellInfoList != null) {
            cellText.append("\n\tsize:" + cellInfoList.size());
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoGsm) {
                    cellText.append("\n\nType: GSM\nCell Identity:");
                    cellText.append("\n\tCID:" + ((CellInfoGsm) cellInfo).getCellIdentity().getCid());
                    cellText.append("\n\tLAC:" + ((CellInfoGsm) cellInfo).getCellIdentity().getLac());
                    cellText.append("\n\tMCC:" + ((CellInfoGsm) cellInfo).getCellIdentity().getMcc());
                    cellText.append("\n\tMNC:" + ((CellInfoGsm) cellInfo).getCellIdentity().getMnc());
                    cellText.append("\nSignal Strength:" + ((CellInfoGsm) cellInfo).getCellSignalStrength().getDbm() + "dBm");
                }
                if (cellInfo instanceof CellInfoWcdma) {
                    cellText.append("\n\nType: WCDMA\nCell Identity:");
                    cellText.append("\n\tCID:" + ((CellInfoWcdma) cellInfo).getCellIdentity().getCid());
                    cellText.append("\n\tLAC:" + ((CellInfoWcdma) cellInfo).getCellIdentity().getLac());
                    cellText.append("\n\tMCC:" + ((CellInfoWcdma) cellInfo).getCellIdentity().getMcc());
                    cellText.append("\n\tMNC:" + ((CellInfoWcdma) cellInfo).getCellIdentity().getMnc());
                    cellText.append("\nSignal Strength:" + ((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm() + "dBm");
                }
                if (cellInfo instanceof CellInfoLte) {
                    cellText.append("\n\nType: LTE\nCell Identity:");
                    cellText.append("\n\tCI:" + ((CellInfoLte) cellInfo).getCellIdentity().getCi());
                    cellText.append("\n\tTAC:" + ((CellInfoLte) cellInfo).getCellIdentity().getTac());
                    cellText.append("\n\tMCC:" + ((CellInfoLte) cellInfo).getCellIdentity().getMcc());
                    cellText.append("\n\tMNC:" + ((CellInfoLte) cellInfo).getCellIdentity().getMnc());
                    cellText.append("\nSignal Strength:" + ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm() + "dBm");
                }
            }
        }
        TextView towerLocation = (TextView) findViewById(R.id.textView2);
        towerLocation.setText("Location");
        towerLocation.append("\nlat: " + itude.latitude);
        towerLocation.append("\nlng: " + itude.longitude);
        towerLocation.append("\nacc: " + itude.accuracy);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
