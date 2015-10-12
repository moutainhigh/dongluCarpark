package com.donglu.carpark.ui.list;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

import com.donglu.carpark.ui.common.AbstractListView;
import com.donglu.carpark.ui.view.CarparkPayHistoryPresenter;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkMonthlyUserPayHistory;

public class CarparkPayHistoryListView extends AbstractListView<SingleCarparkMonthlyUserPayHistory> {
	CarparkPayHistoryListPresenter presenter;
	public CarparkPayHistoryListView(Composite parent, int style) {
		super(parent, style, SingleCarparkMonthlyUserPayHistory.class,
				new String[]{SingleCarparkMonthlyUserPayHistory.Property.userName.name(),
						SingleCarparkMonthlyUserPayHistory.Property.plateNO.name(),
						SingleCarparkMonthlyUserPayHistory.Property.chargesMoney.name(),
						SingleCarparkMonthlyUserPayHistory.Property.createTime.name(),
						SingleCarparkMonthlyUserPayHistory.Property.operaName.name(),
						SingleCarparkMonthlyUserPayHistory.Property.overdueTime.name(),},
				new String[]{"用户名","车牌号","充值金额","充值时间","操作人","过期时间"},
				new int[]{100,100,100,200,100,200});
		this.setTableTitle("充值记录表");
	}

	@Override
	protected void searchMore() {
		presenter.searMore();
	}


	public CarparkPayHistoryListPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(CarparkPayHistoryListPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void createMenuBarToolItem(ToolBar toolBar_menu) {
	}

	@Override
	protected void createRefreshBarToolItem(ToolBar toolBar_refresh) {
	}
	
}