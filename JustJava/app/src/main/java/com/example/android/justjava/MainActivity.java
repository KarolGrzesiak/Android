package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        String message = createOrderSummary(((name.getText().toString())),(calculatePrice(quantity,5,whippedCreamCheckBox.isChecked(),chocolateCheckBox.isChecked())),(whippedCreamCheckBox.isChecked())
                ,(chocolateCheckBox.isChecked()));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for " + name.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    public void increment(View view) {
        if(quantity==100) {
            Toast.makeText(this,"You can't have more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);

    }
    public void decrement(View view) {
        if(quantity<=0) {
            Toast.makeText(this, "You can't have less than 0 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);

    }
    private int calculatePrice(int amount,int pricePerCup,boolean hasWhippedCream, boolean hasChocolate){
        if(hasWhippedCream){
            pricePerCup+=1;
        }
        if(hasChocolate){
            pricePerCup+=2;
        }
        return amount*pricePerCup;
    }
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        return getString(R.string.order_summary_name,name) + "\r\nAdd whipped cream? " + hasWhippedCream + "\r\nAdd chocolate? " + hasChocolate + "\r\nQuantity: "+ quantity + "\r\nTotal: $" + price + "\r\n" + getString(R.string.thank_you);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}