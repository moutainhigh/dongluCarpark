package com.donglu.carpark.ui.list;

import com.dongluhitec.card.common.ui.WidgetContainer;
import com.dongluhitec.card.common.ui.control.AbstractListPresenter;
import com.dongluhitec.card.common.ui.control.ListViewer;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkReturnAccount;

public class BlackUserPresenter extends AbstractListPresenter<SingleCarparkReturnAccount> {

	protected BlackUserPresenter() {
		super(null, null, null, null);
	}

	@Override
	protected ListViewer<SingleCarparkReturnAccount> createViewer(WidgetContainer container) {
		
		return new ListView(container);
	}
	
}