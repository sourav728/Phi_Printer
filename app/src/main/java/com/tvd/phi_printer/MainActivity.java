package com.tvd.phi_printer;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.aem.api.AEMPrinter;
import com.aem.api.AEMScrybeDevice;
import com.aem.api.IAemScrybe;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button connect, print, unicode_print;
    private AEMScrybeDevice m_Aem;
    ArrayList<String> pairedPrinters = new ArrayList<String>();
    private String BlutoothPrinter;
    private boolean connectPrinterBool;
    private AEMPrinter aemPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = findViewById(R.id.btn_connect);
        print = findViewById(R.id.btn_print);
        unicode_print = findViewById(R.id.btn_unicode_print);
        m_Aem = new AEMScrybeDevice(new IAemScrybe() {
            @Override
            public void onDiscoveryComplete(ArrayList<String> arrayList) {
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairedPrinters = m_Aem.getPairedPrinters();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Printer");

                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.select_dialog_singlechoice, pairedPrinters);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        BlutoothPrinter = pairedPrinters.get(i);

                        try {
                            connectPrinterBool = m_Aem.connectToPrinter(BlutoothPrinter);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "catch error", Toast.LENGTH_SHORT).show();
                        }
                        if (connectPrinterBool) {
                            aemPrinter = m_Aem.getAemPrinter();
                            Toast.makeText(getApplicationContext(), "Printer connected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), " error while connecting", Toast.LENGTH_SHORT).show();
                        }

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String text="English\t\tHello India\n" +
                        "Hindi \t\tनमस्ते भारत\n" +
                        "Bengali \t\tহ্যালো ভারত\n" +
                        "Kannada \t\tಹಲೋ ಭಾರತ\n";
                try {
                   //aemPrinter.printTextAsImageThreeInch(text);
                    aemPrinter.printThreeInch(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                phi_print();
            }
        });
        unicode_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUnicode_print();
            }
        });
    }


    public void phi_print() {
        try {
            aemPrinter.printThreeInch(aligncenter("HUBLI ELECTRICITY SUPPLY COMPANY LTD" + "\n", 45));
            aemPrinter.printThreeInch(aligncenter("540038" + "\n", 38));
            aemPrinter.printThreeInch(space("Sub Division", 16) + ":" + " " + "540038" + "\n");
            aemPrinter.printThreeInch(space("RRNO", 16) + ":" + " " + "RR1234" + "\n");
            aemPrinter.printThreeInch(space("Account ID", 16) + ":" + " " + "1234567890" + "\n");
            aemPrinter.printThreeInch(space("Tariff", 16) + ":" + " " + "LT2A" + "\n");
            aemPrinter.printThreeInch(space("Sanct Load", 14) + ":" + "HP:" + alignright("2", 4) + " " + "KW:" + alignright("3", 4) + "\n");
            aemPrinter.printThreeInch(space("Billing", 8) + ":" + "01-03-2019" + "-" + "01-04-2019" + "\n");
            aemPrinter.printThreeInch(space("Reading Date", 16) + ":" + " " + "01-04-2019" + "\n");
            aemPrinter.printThreeInch(space("BillNo", 7) + ":" + " " + "1234567890" + "-" + "01-04-2019" + "\n");
            aemPrinter.printThreeInch(space("Meter SlNo.", 16) + ":" + " " + "123456" + "\n");
            aemPrinter.printThreeInch(space("Pres Rdg", 16) + ":" + " " + "100" + "\n");
            aemPrinter.printThreeInch(space("Prev Rdg", 16) + ":" + " " + "10" + "\n");
            aemPrinter.printThreeInch(space("Constant", 16) + ":" + " " + "10" + "\n");
            aemPrinter.printThreeInch(space("Consumption", 16) + ":" + " " + "90" + "\n");
            aemPrinter.printThreeInch(space("Average", 16) + ":" + " " + "60" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUnicode_print() {
        int  rightspace = 14, details_right = 10;
        try {
            aemPrinter.printTextAsImageThreeInch(empty(2) + "ಹುಬ್ಬಳ್ಳಿ ವಿದ್ಯುತ್ ಸರಬರಾಜು ಕಂಪನಿ ನಿಯಮಿತ" + "\n");
            aemPrinter.printTextAsImageThreeInch(line(36) + "\n");
            aemPrinter.printTextAsImageThreeInch(empty(16) + "540038" + "\n");
            aemPrinter.printTextAsImageThreeInch("ಉಪ ವಿಭಾಗ/Sub Division" + empty(3) + ":" + rightAppend("540038", details_right));
            aemPrinter.printTextAsImageThreeInch("ಆರ್.ಆರ್.ಸಂಖ್ಯೆ/RRNO" + empty(9) + ":" + rightAppend("RR5565", details_right));
            aemPrinter.printTextAsImageThreeInch("ಖಾತೆ ಸಂಖ್ಯೆ/Account ID" + empty(6) + ":" + rightAppend("1234567890", details_right));
            aemPrinter.printTextAsImageThreeInch(empty(4) + "ಹೆಸರು ಮತ್ತು ವಿಳಾಸ/Name and Address");
            aemPrinter.printTextAsImageThreeInch("ಜಕಾತಿ/Tariff" + empty(14) + ":" + rightAppend("LT2A", details_right));
            aemPrinter.printTextAsImageThreeInch("ಮಂ.ಪ್ರಮಾಣ/Sanct Load" + empty(2) + ": " + "HP:" + rightAppend("3", 6) + " KW:" + "2");
            aemPrinter.printTextAsImageThreeInch("Billing Period" + " " + empty(4) + ": " + "01-03-2019" + "-" + "01-04-2019");
            aemPrinter.printTextAsImageThreeInch("ರೀಡಿಂಗ ದಿನಾಂಕ/Reading Date" + " " + empty(1) + ": " + rightAppend("01-04-2019", details_right));
            aemPrinter.printTextAsImageThreeInch("ಬಿಲ್ ಸಂಖ್ಯೆ/Bill No" + " " + empty(2) + ": " + rightAppend(("1234567890" + "04" + "01"), (details_right + 7)));
            aemPrinter.printTextAsImageThreeInch("ಮೀಟರ್ ಸಂಖ್ಯೆ/Meter SlNo" + " " + empty(3) + ": " + rightAppend("1234", details_right));
            aemPrinter.printTextAsImageThreeInch("ಇಂದಿನ ಮಾಪನ/Pres Rdg" + " " + empty(5) + ": " + rightAppend("100", 9) + "  " + "DL");
            aemPrinter.printTextAsImageThreeInch("ಹಿಂದಿನ ಮಾಪನ/Prev Rdg" + " " + empty(5) + ": " + rightAppend("10", 9));
            aemPrinter.printTextAsImageThreeInch("ಮಾಪನ ಸ್ಥಿರಾಂಕ/Constant" + " " + empty(5) + ": " + rightAppend("10", details_right));
            aemPrinter.printTextAsImageThreeInch("ಬಳಕೆ/Consumption" + " " + empty(10) + ": " + rightAppend("90", details_right));
            aemPrinter.printTextAsImageThreeInch("ಸರಾಸರಿ/Average" + " " + empty(12) + ": " + rightAppend("10", details_right));
            aemPrinter.printTextAsImageThreeInch("ದಾಖಲಿತ ಬೇಡಿಕೆ/Recorded MD" + " " + empty(2) + ": " + rightAppend("3", details_right));
            aemPrinter.printTextAsImageThreeInch("ಪವರ ಫ್ಯಾಕ್ಟರ/Power Factor" + " " + empty(3) + ": " + rightAppend("3", details_right));
            aemPrinter.printTextAsImageThreeInch("ನಿಗದಿತ ಶುಲ್ಕ/Fixed Charges (Unit,Rate,Amount)");
            aemPrinter.printTextAsImageThreeInch(rightAppend("1.02", 7) + rightAppend("x", 2) + rightAppend("30", 6) + rightAppend("x", 2) + rightAppend("50.0", 6) + rightAppend("=", 4) + rightAppend("1500", rightspace));
            aemPrinter.printTextAsImageThreeInch("ವಿದ್ಯುತ್ ಶುಲ್ಕ/Energy Charges(Unit,Rate,Amount)");
            aemPrinter.printTextAsImageThreeInch(rightAppend("1.02", 7) + rightAppend("x", 2) + rightAppend("30", 6) + rightAppend("x", 2) + rightAppend("50.0", 6) + rightAppend("=", 4) + rightAppend("1500", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಎಫ್.ಎ.ಸಿ/FAC" + " : " + rightAppend(String.valueOf("30"), 8) + " x" + alignright("0.13", 6) + alignright("39.00", (rightspace - 2)));
            aemPrinter.printTextAsImageThreeInch("ರಿಯಾಯಿತಿ/Rebates/TOD" + empty(2) + "(-) : " + rightAppend("100.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಪಿ.ಎಫ್ ದಂಡ/PF Penalty" + " " + empty(4) + ": " + rightAppend("120.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಎಂ.ಡಿ.ದಂಡ/MD Penalty" + " " + empty(6) + ": " + rightAppend("100.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಬಡ್ಡಿ/Interest @1%" + " " + empty(9) + ": " + rightAppend("100.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಇತರೆ/Others" + " " + empty(14) + ": " + rightAppend("10.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ತೆರಿಗೆ/Tax @9%" + " " + empty(14) + ": " + rightAppend("20.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಬಿಲ್ ಮೊತ್ತ/Cur Bill Amt" + " " + empty(4) + ": " + rightAppend("200.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಬಾಕಿ/Arrears" + " " + empty(14) + ": " + rightAppend("50.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಡಿಎಲ್ ಬಿಲ್/DL Bill" + empty(5) + "(-) : " + rightAppend("10.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಜಮಾ/Credits & Adj" + " " + empty(7) + ": " + rightAppend("10.00", rightspace));
            aemPrinter.printTextAsImageThreeInch("ಸರ್ಕಾರದ ಸಹಾಯಧನ/GOK" + empty(2) + "(-) : " + rightAppend("20.00", rightspace));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String aligncenter(String msg, int len) {
        int count = msg.length();
        int value = len - count;
        int append = (value / 2);
        return space(" ", append) + msg + space(" ", append);
    }

    public String space(String s, int len) {
        int temp;
        StringBuilder spaces = new StringBuilder();
        temp = len - s.length();
        for (int i = 0; i < temp; i++) {
            spaces.append(" ");
        }
        return (s + spaces);
    }

    public String alignright(String msg, int len) {
        StringBuilder msgBuilder = new StringBuilder(msg);
        for (int i = 0; i < len - msgBuilder.length(); i++) {
            msgBuilder.insert(0, " ");
        }
        msg = msgBuilder.toString();
        msg = String.format("%" + len + "s", msg);
        return msg;
    }

    public String empty(int length) {
        StringBuilder sb5 = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb5.append(" ");
        }
        return (sb5.toString());
    }

    //Dotted line
    public String line(int length) {
        StringBuilder sb5 = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb5.append("-");
        }
        return (sb5.toString());
    }

    public String rightAppend(String str, int maxlen) {
        StringBuilder retStr = new StringBuilder();
        if (str.length() < maxlen) {
            for (int i = 0; i < maxlen - str.length(); i++) {
                retStr.append(" ");
            }
            retStr.append(str);
        }
        return retStr.toString();

    }
}
