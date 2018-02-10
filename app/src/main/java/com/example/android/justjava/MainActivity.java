/**

 * IMPORTANT: Make sure you are using the correct package name. 

 * This example uses the package name:

 * package com.example.android.justjava

 * If you get an error when copying this code into Android studio, update it to match teh package name found

 * in the project's AndroidManifest.xml file.

 **/

package com.example.android.justjava;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasCream = whippedCream.isChecked();

        CheckBox chocolateTop = (CheckBox) findViewById(R.id.chocolate_topping);
        boolean hasChocolate = chocolateTop.isChecked();

        EditText nameInput = (EditText) findViewById(R.id.name_input);
        Editable hisname = nameInput.getText();

        int price = calculatePrice();
        String priceMessage = createOrderSummary(quantity, hasCream, hasChocolate, hisname);
//        displayMessage(priceMessage);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     *
     */

    private int calculatePrice() {
        int price = quantity * 5;
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean creamPrice = whippedCream.isChecked();
        if (creamPrice) {
            int cream = quantity * 1;
            price += cream;
        }

        CheckBox chocolateTop = (CheckBox) findViewById(R.id.chocolate_topping);
        boolean chocolatePrice = chocolateTop.isChecked();
        if (chocolatePrice) {
            int chocolate = quantity * 2;
            price += chocolate;
        }
        return price;
    }

    /**
     * This method is called when the plus button is clicked.
     */

    public void increment(View view) {
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "You can't order more than 100 coffees!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */

    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than 1 coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String createOrderSummary (int quantity, boolean creamed, boolean chocolate, Editable name){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, creamed);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, chocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price) + calculatePrice();
           NumberFormat.getCurrencyInstance().format(calculatePrice());
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

}