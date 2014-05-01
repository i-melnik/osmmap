package com.itos.opm.screen;

import java.util.List;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;

public class MyOverlay extends ItemizedIconOverlay<OverlayItem>
{

	protected Context mContext;

	public MyOverlay(Context pContext, List<OverlayItem> pList)
	{
		super(pContext, pList, new OnItemGestureListener<OverlayItem>()
		{

			@Override
			public boolean onItemLongPress(int arg0, OverlayItem arg1)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onItemSingleTapUp(int arg0, OverlayItem arg1)
			{
				// TODO Auto-generated method stub
				return false;
			}

		});
		mContext = pContext;
	}

	@Override
	protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView)
	{
		// Toast.makeText(mContext, "Item " + index + " has been tapped!",
		// Toast.LENGTH_SHORT).show();
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
