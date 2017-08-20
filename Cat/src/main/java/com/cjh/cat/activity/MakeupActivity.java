package com.cjh.cat.activity;

import static com.cjh.cat.part.PortraitPart.BACKGROUND;
import static com.cjh.cat.part.PortraitPart.BUBBLE;
import static com.cjh.cat.part.PortraitPart.EAR;
import static com.cjh.cat.part.PortraitPart.EXPRESSION;
import static com.cjh.cat.part.PortraitPart.EYE;
import static com.cjh.cat.part.PortraitPart.FACE;
import static com.cjh.cat.part.PortraitPart.FEATURE;
import static com.cjh.cat.part.PortraitPart.FOOT;
import static com.cjh.cat.part.PortraitPart.HAIR_COLOR;
import static com.cjh.cat.part.PortraitPart.MOUTH;
import static com.cjh.cat.part.PortraitPart.NOSE;
import static com.cjh.cat.part.PortraitPart.OTHER;
import static com.cjh.cat.part.PortraitPart.SKIN_COLOR;
import static com.cjh.cat.part.PortraitPart.WHISKERS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cjh.cat.R;
import com.cjh.cat.common.Utils;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.portrait.PortraitsView;
import com.cjh.cat.table.ScrollTable;
import com.cjh.cat.util.TableResource;

/** 编辑界面 */
public class MakeupActivity extends Activity {
	
	private PortraitsView portraitsView;
	private ScrollTable componentTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_makeup);
		
		portraitsView = (PortraitsView) findViewById(R.id.portraitsView);
		componentTable = (ScrollTable) findViewById(R.id.componentTable);
		Button btnBack = (Button) findViewById(R.id.makeup_btnBack);
		Button btnSave = (Button) findViewById(R.id.makeup_btnSave);
		Button btnShare = (Button) findViewById(R.id.makeup_btnShare);

		
		portraitsView.init(componentTable);
		componentTable.init(createTableResources(), portraitsView);
		componentTable.setDefaultSelectedTitle(0);

		btnBack.setOnClickListener(createBackButtonListener());
		btnSave.setOnClickListener(createSaveButtonListener());
		btnShare.setOnClickListener(createShareButtonListener());
	}

	private OnClickListener createBackButtonListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		};
	}

	private OnClickListener createSaveButtonListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				File file = createSaveFile();
				saveToFile(file);
				Toast.makeText(MakeupActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		};
	}

	private OnClickListener createShareButtonListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				File file = saveToFile(createSaveFile());
				startShareActivity(file);
			}
		};
	}

	private void startShareActivity(File file) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.setType("image/png");
		startActivity(Intent.createChooser(shareIntent, getTitle()));
	}

	private File saveToFile(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			Bitmap bitmap = Utils.saveViewBitmap(portraitsView);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	private File createSaveFile() {
		File sdCardDir = Environment.getExternalStorageDirectory();
		String strPath = "FaceFun/facefun" + System.currentTimeMillis() + ".png";
		File file = new File(sdCardDir, strPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}

	private List<TableResource> createTableResources() {

		List<TableResource> tableResources = new ArrayList<TableResource>();
		
		TableResource tableResource = createTableResource(EAR, R.string.title_ear, R.drawable.ear01,
				R.drawable.ear15);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(HAIR_COLOR, R.string.title_hair_color, R.color.hair_color_01,
				R.color.hair_color_25);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(FACE, R.string.title_face_shape, R.drawable.face01, R.drawable.face06);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(SKIN_COLOR, R.string.title_skin_color, R.color.skin_color_01,
				R.color.skin_color_09);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(WHISKERS, R.string.title_whiskers, R.drawable.whiskers00, R.drawable.whiskers10);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(EYE, R.string.title_eye, R.drawable.eye01, R.drawable.eye26);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(PortraitPart.EYE_COLOR, R.string.title_eye_color, 
				R.color.eye_color_01, R.color.eye_color_03);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(MOUTH, R.string.title_mouth, R.drawable.mouth00, R.drawable.mouth22);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(NOSE, R.string.title_nose, R.drawable.nose00, R.drawable.nose03);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(FEATURE, R.string.title_feature, R.drawable.feature00, R.drawable.feature01);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(OTHER, R.string.title_other, R.drawable.other00, R.drawable.other06);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(FOOT, R.string.title_foot, R.drawable.foot01, R.drawable.foot05);
		tableResources.add(tableResource);

		tableResource = createTableResource(BACKGROUND, R.string.title_background, R.drawable.bg01, R.drawable.bg02);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(EXPRESSION, R.string.title_expression, R.drawable.express00,
				R.drawable.express02);
		tableResources.add(tableResource);
		
		tableResource = createTableResource(BUBBLE, R.string.title_bubble, R.drawable.bubble0, R.drawable.bubble20036);
		tableResources.add(tableResource);
		
		return tableResources;
	}

	private TableResource createTableResource(PortraitPart component, int titleId, int picIdStart, int picIdEnd) {
		TableResource tableResource = new TableResource();
		tableResource.setPortraitPart(component);
		tableResource.setTitle(getResources().getString(titleId));
		tableResource.setResourceIds(getIds(picIdStart, picIdEnd));
		return tableResource;
	}

	private int[] getIds(int startId, int endId) {
		int[] ids = new int[endId - startId + 1];
		for (int j = 0; j < ids.length; j++) {
			ids[j] = startId++;
		}
		return ids;
	}
}
