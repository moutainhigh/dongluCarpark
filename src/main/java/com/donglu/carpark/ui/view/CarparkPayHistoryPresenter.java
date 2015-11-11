package com.donglu.carpark.ui.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.eclipse.swt.widgets.Composite;

import com.donglu.carpark.service.CarparkDatabaseServiceProvider;
import com.donglu.carpark.ui.common.Presenter;
import com.donglu.carpark.ui.list.CarparkPayHistoryListPresenter;
import com.donglu.carpark.ui.list.CarparkPayHistoryListView;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkSystemUser;
import com.google.inject.Inject;

public class CarparkPayHistoryPresenter  implements Presenter{
	private CarparkPayHistoryView view;
	int max=0;
	int size=50;
	String userName, operaName;
	Date start,  end;
	CarparkPayHistoryListView carparkPayHistoryListView;
	
	@Inject
	private CarparkDatabaseServiceProvider sp;
	@Inject
	private CarparkPayHistoryListPresenter carparkPayHistoryListPresenter;
	
	public void go(Composite parent){
		view=new CarparkPayHistoryView(parent, parent.getStyle());
		view.setCarparkPayHistoryPresenter(this);
		carparkPayHistoryListPresenter.go(view.getListComposite());
		
		List<SingleCarparkSystemUser> findAll = new ArrayList<>();
		SingleCarparkSystemUser e = new SingleCarparkSystemUser();
		e.setUserName("全部");
		findAll.add(e);
		findAll.addAll(sp.getSystemUserService().findAll());
		view.setComboValue(findAll);
	}
	
	public void searchCharge(String userName, String operaName, Date start, Date end) {
		this.userName=userName;
		this.operaName=operaName;
		this.start=start;
		this.end=end;
		carparkPayHistoryListPresenter.searchCharge(userName, operaName, start, end);
	}

	public void export() {
		carparkPayHistoryListPresenter.export();
	}

}
