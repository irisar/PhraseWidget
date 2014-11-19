package com.lovelydeveloper.phrasewidget;

import com.lovelydeveloper.phrasewidget.db.CategoryBean;
import com.lovelydeveloper.phrasewidget.db.CategoryController;
import com.lovelydeveloper.phrasewidget.db.DataBaseHelper;
import com.lovelydeveloper.phrasewidget.db.PhraseBean;
import com.lovelydeveloper.phrasewidget.db.PhraseController;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.RemoteViews;

public class PhraseWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		
		//Copiamos la base de datos
		DataBaseHelper myDbHelper = new DataBaseHelper(context);
        try {
        	myDbHelper.createDataBase();
        } catch (Exception e) {
        	Log.d("PhraseWidget", "Excepcion al crear base de datos: " + e.toString());
        }
        try {
			PhraseController controller = new PhraseController(context);
			PhraseBean phrase = controller.getRandomPhrase();
			
	        remoteViews.setImageViewBitmap(R.id.text, convertToImg(phrase, context));
	        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        } catch (Exception e) {
        	Log.d("PhraseWidget", "Excepcion: " + e.toString());
        }
        Log.d("PhraseWidget", "Ejecutado");
	}

    public Bitmap convertToImg(PhraseBean phrase, Context context) {

    	//Obtenemos la categoria
		CategoryController controller = new CategoryController(context);
		CategoryBean category = controller.getCategory(phrase.getCategory());
    	
    	String myPhrase = phrase.getText();
    	Log.d("PhraseWidget", "Phrase: " + myPhrase);
    	String[] textArray = myPhrase.split("\\\\n");
    	Log.d("PhraseWidget", "size: " + textArray.length);
    	Bitmap btmText = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_4444);
        Canvas cnvText = new Canvas(btmText);
        
        Typeface tf = Typeface.createFromAsset(context.getAssets(),phrase.getFont());
        
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(tf);
        paint.setTextAlign(Align.CENTER);
        
 

        int yPos = phrase.getSize() + 20;
        for (int i = 0; i < textArray.length; i++) {
        	String text = textArray[i];

        	//Comprobamos si hay código de color
        	if (text.indexOf("<blue>") > -1) { //Etiqueta color azul
        		paint.setColor(Color.BLUE);
        		text = text.replaceAll("<blue>", "");
        	} else if (text.indexOf("<pink>") > -1) { //Etiqueta color rosa
        		paint.setColor(Color.MAGENTA);
        		text = text.replaceAll("<pink>", "");
        	} else { //Lo obtenemos de la categoria
        		 //TODO añadir color de la categoria
        		
            }
        	
        	//Comprobamos el tamaño del texto
        	int size = phrase.getSize();
        	int corrector = 0;
        	if (text.indexOf("<big>") > -1) { //Etiqueta color azul
        		corrector = 30;
        		text = text.replaceAll("<big>", "");
        	} else if (text.indexOf("<little>") > -1) { //Etiqueta color rosa
        		corrector = -30;
        		text = text.replaceAll("<little>", "");
        	}
        	paint.setTextSize(size + corrector);
        	
        	float halfLength = text.length() / 2f;
        	int xPos = (cnvText.getWidth() / 2) - ((int)halfLength);
        	cnvText.drawText(text, xPos, yPos, paint);
        	yPos = yPos + (size + corrector) + 20;
        }
        return btmText;      
    }
}
