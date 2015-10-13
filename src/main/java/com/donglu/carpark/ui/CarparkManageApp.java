package com.donglu.carpark.ui;


import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.wb.rcp.databinding.BeansListObservableFactory;
import org.eclipse.wb.rcp.databinding.TreeBeanAdvisor;
import org.eclipse.wb.rcp.databinding.TreeObservableLabelProvider;
import org.eclipse.wb.swt.SWTResourceManager;

import com.beust.jcommander.JCommander;
import com.donglu.carpark.App;
import com.donglu.carpark.info.CarparkChargeInfo;
import com.donglu.carpark.model.CarparkModel;
import com.donglu.carpark.model.InOutHistoryModel;
import com.donglu.carpark.model.SystemUserModel;
import com.donglu.carpark.model.UserModel;
import com.donglu.carpark.wizard.AddBlackUserWizard;
import com.dongluhitec.card.common.ui.CommonUIFacility;
import com.dongluhitec.card.common.ui.uitl.JFaceUtil;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkCarpark;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkInOutHistory;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkSystemUser;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkUser;
import com.dongluhitec.card.domain.db.singlecarpark.SystemSettingTypeEnum;
import com.dongluhitec.card.domain.util.StrUtil;
import com.dongluhitec.card.ui.main.DongluUIAppConfigurator;
import com.dongluhitec.card.ui.main.javafx.DongluJavaFXModule;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.donglu.carpark.ui.list.CarparkPayHistoryListView;

public class CarparkManageApp implements App{
	private DataBindingContext m_bindingContext;

	protected Shell shell;
	private Table table;
	private Table table_2;
	private Text text_inout_plateNo;
	private Text text_inout_userName;
	private Text text_inout_operaName;
	private Table table_3;
	private Text text_3;
	private Text text_4;
	private SashForm sashForm;
	private Table table_5;
	
	@Inject
	CommonUIFacility commonui;
	
	private Table table_user;
	private Text text_7;
	private Text text_8;
	private Table table_6;
	private ToolBar carparkConfigToolBar;
	private Text text_setting_dataBaseSave;
	private Text text_setting_imgSave;
	private Text text_setting_imgSaveDays;
	private Text text_12;
	@Inject
	private CarparkManagePresenter presenter;
	
	private TreeViewer treeViewer;
	
	private CarparkModel carparkModel;
	
	private UserModel userModel;
	
	private SystemUserModel systemUserModel;
	
	private InOutHistoryModel inOutHistoryModel;
	private TableViewer tableViewer_user;
	private TableViewer tableViewer_1;
	private TableViewer tableViewer;
	private TableViewer tableViewer_2;

	private Combo combo_inout_carType;

	private Combo combo_inout_inorout;
	private Label label_inout_nowCount;
	private Label label_inout_totalCount;

	private DateChooserCombo dateChooserCombo_inout_start;

	private DateChooserCombo dateChooserCombo_inout_end;
	
	private Map<SystemSettingTypeEnum, String> mapSystemSetting=Maps.newHashMap();

	private Composite composite_returnAccount_search;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					 DongluUIAppConfigurator configurator = new DongluUIAppConfigurator();
	         new JCommander(configurator, args);
					Injector createInjector = Guice.createInjector(new DongluJavaFXModule());
					CarparkManageApp window = createInjector.getInstance(CarparkManageApp.class);
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		init();
		createContents();
		shell.open();
		shell.setMaximized(true);
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		System.exit(0);
	}

	private void init() {
		presenter.setView(this);
		carparkModel=new CarparkModel();
		userModel=new UserModel();
		systemUserModel=new SystemUserModel();
		inOutHistoryModel=new InOutHistoryModel();
		presenter.setCarparkModel(carparkModel);
		presenter.setUserModel(userModel);
		presenter.setSystemUserModel(systemUserModel);
		presenter.setInOutHistoryModel(inOutHistoryModel);
		
		for (SystemSettingTypeEnum t : SystemSettingTypeEnum.values()) {
			mapSystemSetting.put(t, null);
		}
		presenter.init();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(896, 621);
		shell.setText("停车场管理界面");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("停车场管理");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		sashForm = new SashForm(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(composite_1, SWT.VERTICAL);
		
		Composite composite_3 = new Composite(sashForm_1, SWT.BORDER);
		composite_3.setLayout(new GridLayout(2, false));
		
		Label label = new Label(composite_3, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 88;
		label.setLayoutData(gd_label);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setText("停车场设置");
		
		carparkConfigToolBar = new ToolBar(composite_3, SWT.FLAT | SWT.RIGHT);
		carparkConfigToolBar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		ToolItem toolItem_add = new ToolItem(carparkConfigToolBar, SWT.NONE);
		toolItem_add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.addCarpark();
			}
		});
		toolItem_add.setText("添加");
		
		ToolItem toolItem_1 = new ToolItem(carparkConfigToolBar, SWT.NONE);
		toolItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.deleteCarpark();
			}
		});
		toolItem_1.setText("删除");
		
		treeViewer = new TreeViewer(composite_3, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.refreshCarparkCharge();
			}
		});
		tree.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn treeColumn = treeViewerColumn.getColumn();
		treeColumn.setWidth(100);
		treeColumn.setText("停车场");
		
		Composite composite_4 = new Composite(sashForm_1, SWT.NONE);
		composite_4.setLayout(new GridLayout(2, false));
		
		Label label_1 = new Label(composite_4, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_1.setText("收费设置");
		
		ToolBar toolBar_1 = new ToolBar(composite_4, SWT.FLAT | SWT.RIGHT);
		toolBar_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		ToolItem toolItem_2 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_2.setToolTipText("添加临时收费设置");
		toolItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.addTempCharge(null);
			}
		});
		toolItem_2.setText("添加临时");
		
		ToolItem toolItem_14 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_14.setToolTipText("添加固定收费设置");
		toolItem_14.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.addMonthCharge();
			}
		});
		toolItem_14.setText("添加固定");
		
		ToolItem toolItem_3 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.deleteCarparkCharge();
			}
		});
		toolItem_3.setText("删除");
		
		ToolItem toolItem_8 = new ToolItem(toolBar_1, SWT.NONE);
		toolItem_8.setToolTipText("修改");
		toolItem_8.setText("修改");
		
		tableViewer = new TableViewer(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TableViewerColumn tableViewerColumn_20 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_5 = tableViewerColumn_20.getColumn();
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("编码");
		
		TableViewerColumn tableViewerColumn_21 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_6 = tableViewerColumn_21.getColumn();
		tblclmnNewColumn_6.setWidth(100);
		tblclmnNewColumn_6.setText("名称");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_1.getColumn();
		tblclmnNewColumn.setWidth(114);
		tblclmnNewColumn.setText("收费类型");
		sashForm_1.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {1});
		
		TabItem tabItem_5 = new TabItem(tabFolder, SWT.NONE);
		tabItem_5.setText("固定车设置");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		composite_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		tabItem_5.setControl(composite_2);
		composite_2.setLayout(new GridLayout(2, false));
		
		Group group_3 = new Group(composite_2, SWT.NONE);
		group_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		group_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		group_3.setLayout(new GridLayout(5, false));
		
		Label lblNewLabel_9 = new Label(group_3, SWT.NONE);
		lblNewLabel_9.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_9.setText("姓名");
		
		text_7 = new Text(group_3, SWT.BORDER);
		text_7.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_10 = new Label(group_3, SWT.NONE);
		lblNewLabel_10.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_10.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_10.setText("车牌");
		
		text_8 = new Text(group_3, SWT.BORDER);
		text_8.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button button_2 = new Button(group_3, SWT.NONE);
		button_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_2.setText("查询");
		
		Label label_2 = new Label(composite_2, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_2.setText("固定用户设置");
		
		ToolBar toolBar_user = new ToolBar(composite_2, SWT.FLAT | SWT.RIGHT);
		toolBar_user.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		ToolItem toolItem_7 = new ToolItem(toolBar_user, SWT.NONE);
		toolItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.monthUserPay();
			}
		});
		toolItem_7.setText("充值");
		
		ToolItem toolItem_4 = new ToolItem(toolBar_user, SWT.NONE);
		toolItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.addCarparkUser();
			}
		});
		toolItem_4.setText("添加");
		
		ToolItem toolItem_delete = new ToolItem(toolBar_user, SWT.NONE);
		toolItem_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.delCarparkUser();
			}
		});
		toolItem_delete.setText("删除");
		
		ToolItem toolItem_edit = new ToolItem(toolBar_user, SWT.NONE);
		toolItem_edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.editCarparkUser();
			}
		});
		toolItem_edit.setText("修改");
		
		tableViewer_user = new TableViewer(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table_user = tableViewer_user.getTable();
		table_user.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table_user.setLinesVisible(true);
		table_user.setHeaderVisible(true);
		table_user.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TableViewerColumn tableViewerColumn_id = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_id = tableViewerColumn_id.getColumn();
		tableColumn_id.setWidth(78);
		tableColumn_id.setText("编号");
		
		TableViewerColumn tableViewerColumn_name = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_name = tableViewerColumn_name.getColumn();
		tableColumn_name.setWidth(70);
		tableColumn_name.setText("姓名");
		
		TableViewerColumn tableViewerColumn_plateNo = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_plateNo = tableViewerColumn_plateNo.getColumn();
		tableColumn_plateNo.setWidth(104);
		tableColumn_plateNo.setText("车牌号");
		
		TableViewerColumn tableViewerColumn_address = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_address = tableViewerColumn_address.getColumn();
		tableColumn_address.setWidth(134);
		tableColumn_address.setText("住址");
		
		TableViewerColumn tableViewerColumn_type = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_type = tableViewerColumn_type.getColumn();
		tableColumn_type.setWidth(81);
		tableColumn_type.setText("用户类型");
		
		TableViewerColumn tableViewerColumn_validTo = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_validTo = tableViewerColumn_validTo.getColumn();
		tableColumn_validTo.setWidth(111);
		tableColumn_validTo.setText("有效期");
		
		TableViewerColumn tableViewerColumn_carNo = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_carparkNo = tableViewerColumn_carNo.getColumn();
		tableColumn_carparkNo.setWidth(74);
		tableColumn_carparkNo.setText("车位");
		
		TableViewerColumn tableViewerColumn_remark = new TableViewerColumn(tableViewer_user, SWT.NONE);
		TableColumn tableColumn_remark = tableViewerColumn_remark.getColumn();
		tableColumn_remark.setWidth(100);
		tableColumn_remark.setText("备注");
		
		TabItem tbtmNewItem_2 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_2.setText("记录查询");
		
		Composite composite_5 = new Composite(tabFolder, SWT.NONE);
		composite_5.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		tbtmNewItem_2.setControl(composite_5);
		composite_5.setLayout(new GridLayout(1, false));
		
		TabFolder tabFolder_searchHistory = new TabFolder(composite_5, SWT.BOTTOM);
		tabFolder_searchHistory.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tabItem = new TabItem(tabFolder_searchHistory, SWT.NONE);
		tabItem.setText("进出记录查询");
		
		Composite composite_7 = new Composite(tabFolder_searchHistory, SWT.NONE);
		tabItem.setControl(composite_7);
		composite_7.setLayout(new GridLayout(1, false));
		
		Group group = new Group(composite_7, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		group.setLayout(new GridLayout(9, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		group.setText("查询");
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("车牌");
		
		text_inout_plateNo = new Text(group, SWT.BORDER);
		GridData gd_text_inout_plateNo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_inout_plateNo.widthHint = 71;
		text_inout_plateNo.setLayoutData(gd_text_inout_plateNo);
		text_inout_plateNo.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("用户");
		
		text_inout_userName = new Text(group, SWT.BORDER);
		text_inout_userName.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_inout_userName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_1.setText("开始时间");
		
		dateChooserCombo_inout_start = new DateChooserCombo(group, SWT.BORDER);
		dateChooserCombo_inout_start.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		GridData gd_dateChooserCombo_inout_start = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_dateChooserCombo_inout_start.widthHint = 117;
		dateChooserCombo_inout_start.setLayoutData(gd_dateChooserCombo_inout_start);
		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("操作员");
		
		text_inout_operaName = new Text(group, SWT.BORDER);
		text_inout_operaName.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inOutHistoryModel.setListSearch(new ArrayList<>());
				Date end = StrUtil.parseDate(dateChooserCombo_inout_end.getText());
				Date s = StrUtil.parseDate(dateChooserCombo_inout_start.getText());
				presenter.search(text_inout_plateNo.getText(),text_inout_userName.getText(),s,end,
						text_inout_operaName.getText(),combo_inout_carType.getText(),combo_inout_inorout.getText());
			}
		});
		button.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button.setText("查询");
		
		Label label_9 = new Label(group, SWT.NONE);
		label_9.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_9.setText("车辆类型");
		
		ComboViewer comboViewer = new ComboViewer(group, SWT.NONE);
		combo_inout_carType = comboViewer.getCombo();
		combo_inout_carType.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		combo_inout_carType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setLabelProvider(new LabelProvider());
		comboViewer.setInput(new String[]{"全部","固定车","临时车"});
		combo_inout_carType.select(0);
		Label label_10 = new Label(group, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_10.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_10.setText("是否出场");
		
		ComboViewer comboViewer_1 = new ComboViewer(group, SWT.NONE);
		combo_inout_inorout = comboViewer_1.getCombo();
		combo_inout_inorout.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		combo_inout_inorout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboViewer_1.setContentProvider(new ArrayContentProvider());
		comboViewer_1.setLabelProvider(new LabelProvider());
		comboViewer_1.setInput(new String[]{"无","是","否"});
		combo_inout_inorout.select(0);
		
		Label lblNewLabel_7 = new Label(group, SWT.NONE);
		lblNewLabel_7.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_7.setText("结束时间");
		
		dateChooserCombo_inout_end = new DateChooserCombo(group, SWT.BORDER);
		dateChooserCombo_inout_end.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		dateChooserCombo_inout_end.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_13 = new Label(group, SWT.NONE);
		label_13.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_13.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_13.setText("统计金额");
		
		text_12 = new Text(group, SWT.BORDER);
		text_12.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_12.setEnabled(false);
		text_12.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button button_10 = new Button(group, SWT.NONE);
		button_10.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_12.setText("11111");
			}
		});
		button_10.setText("统计");
		
		tableViewer_2 = new TableViewer(composite_7, SWT.BORDER | SWT.FULL_SELECTION);
		table_2 = tableViewer_2.getTable();
		table_2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table_2.setLinesVisible(true);
		table_2.setHeaderVisible(true);
		table_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_5.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("车牌号");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_3.getColumn();
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("车辆类型");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_2.getColumn();
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("用户名");
		
		TableViewerColumn tableViewerColumn_27 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_20 = tableViewerColumn_27.getColumn();
		tableColumn_20.setWidth(150);
		tableColumn_20.setText("进场设备");
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_6.getColumn();
		tblclmnNewColumn_2.setWidth(200);
		tblclmnNewColumn_2.setText("进场时间");
		
		TableViewerColumn tableViewerColumn_28 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_21 = tableViewerColumn_28.getColumn();
		tableColumn_21.setWidth(150);
		tableColumn_21.setText("出场设备");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_7.getColumn();
		tblclmnNewColumn_3.setWidth(200);
		tblclmnNewColumn_3.setText("出场时间");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_8.getColumn();
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText("操作员");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_4.getColumn();
		tableColumn_3.setWidth(85);
		tableColumn_3.setText("应收金额");
		
		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_8 = tableViewerColumn_13.getColumn();
		tableColumn_8.setWidth(85);
		tableColumn_8.setText("实收金额");
		
		TableViewerColumn tableViewerColumn_17 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_12 = tableViewerColumn_17.getColumn();
		tableColumn_12.setWidth(85);
		tableColumn_12.setText("免费金额");
		
		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tableColumn_9 = tableViewerColumn_14.getColumn();
		tableColumn_9.setWidth(85);
		tableColumn_9.setText("归账编号");
		
		Composite composite_16 = new Composite(composite_7, SWT.NONE);
		composite_16.setLayout(new GridLayout(4, false));
		composite_16.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label_inout_nowCount = new Label(composite_16, SWT.RIGHT);
		GridData gd_label_inout_nowCount = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_inout_nowCount.widthHint = 43;
		label_inout_nowCount.setLayoutData(gd_label_inout_nowCount);
		label_inout_nowCount.setText("0");
		
		Label label_15 = new Label(composite_16, SWT.NONE);
		label_15.setText("/");
		
		label_inout_totalCount = new Label(composite_16, SWT.NONE);
		GridData gd_label_inout_totalCount = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_inout_totalCount.widthHint = 93;
		label_inout_totalCount.setLayoutData(gd_label_inout_totalCount);
		label_inout_totalCount.setText("0");
		
		Button button_11 = new Button(composite_16, SWT.NONE);
		button_11.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (inOutHistoryModel.getCountSearchAll()<=inOutHistoryModel.getCountSearch()) {
					return;
				}
				Date end = StrUtil.parseDate(dateChooserCombo_inout_end.getText());
				Date s = StrUtil.parseDate(dateChooserCombo_inout_start.getText());
				presenter.search(text_inout_plateNo.getText(),text_inout_userName.getText(),s,end,
						text_inout_operaName.getText(),combo_inout_carType.getText(),combo_inout_inorout.getText());
			}
		});
		button_11.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		button_11.setText("更多");
		
		TabItem tabItem_1 = new TabItem(tabFolder_searchHistory, SWT.NONE);
		tabItem_1.setText("归账查询");
		
		Composite composite_6 = new Composite(tabFolder_searchHistory, SWT.NONE);
		tabItem_1.setControl(composite_6);
		composite_6.setLayout(new GridLayout(1, false));
		
		Group group_1 = new Group(composite_6, SWT.NONE);
		group_1.setLayout(new GridLayout(9, false));
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		group_1.setText("查询");
		
		Label lblNewLabel_3 = new Label(group_1, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("操作员");
		
		text_3 = new Text(group_1, SWT.BORDER);
		text_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_4 = new Label(group_1, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("归账人");
		
		text_4 = new Text(group_1, SWT.BORDER);
		text_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_5 = new Label(group_1, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_5.setText("时间");
		
		DateTime dateTime_1 = new DateTime(group_1, SWT.BORDER);
		dateTime_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		
		Label lblNewLabel_8 = new Label(group_1, SWT.NONE);
		lblNewLabel_8.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_8.setText("终止时间");
		
		DateTime dateTime_3 = new DateTime(group_1, SWT.BORDER);
		dateTime_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		
		Button btnNewButton = new Button(group_1, SWT.NONE);
		btnNewButton.setText("查询");
		
		TableViewer tableViewer_3 = new TableViewer(composite_6, SWT.BORDER | SWT.FULL_SELECTION);
		table_3 = tableViewer_3.getTable();
		table_3.setHeaderVisible(true);
		table_3.setLinesVisible(true);
		table_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer_3, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_9.getColumn();
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("归账人");
		
		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewer_3, SWT.NONE);
		TableColumn tableColumn_5 = tableViewerColumn_10.getColumn();
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("操作员");
		
		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(tableViewer_3, SWT.NONE);
		TableColumn tableColumn_6 = tableViewerColumn_11.getColumn();
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("金额");
		
		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(tableViewer_3, SWT.NONE);
		TableColumn tableColumn_7 = tableViewerColumn_12.getColumn();
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("时间");
		
		TabItem tabItem_7 = new TabItem(tabFolder_searchHistory, SWT.NONE);
		tabItem_7.setText("充值记录查询");
		
		Composite composite_18 =presenter.getCarparkPayHistoryPresenter().getView(tabFolder_searchHistory, SWT.NONE);
		tabItem_7.setControl(composite_18);
		
		TabItem tabItem_2 = new TabItem(tabFolder_searchHistory, SWT.NONE);
		tabItem_2.setText("New Item");
		
		composite_returnAccount_search = new Composite(tabFolder_searchHistory, SWT.NONE);
		tabItem_2.setControl(composite_returnAccount_search);
		presenter.getReturnAccountPresenter().go(composite_returnAccount_search);
		composite_returnAccount_search.setLayout(new FillLayout(SWT.HORIZONTAL));
		TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("系统用户");
		
		Composite composite_9 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_1.setControl(composite_9);
		composite_9.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel_6 = new Label(composite_9, SWT.NONE);
		lblNewLabel_6.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel_6.setText("系统用户设置");
		
		ToolBar toolBar_systemUser = new ToolBar(composite_9, SWT.FLAT | SWT.RIGHT);
		toolBar_systemUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		ToolItem toolItem_addSystemUser = new ToolItem(toolBar_systemUser, SWT.NONE);
		toolItem_addSystemUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.addSystemUser();
			}
		});
		toolItem_addSystemUser.setText("添加");
		
		ToolItem toolItem_10 = new ToolItem(toolBar_systemUser, SWT.NONE);
		toolItem_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.deleteSystemUser();
			}
		});
		toolItem_10.setText("删除");
		
		ToolItem toolItem_11 = new ToolItem(toolBar_systemUser, SWT.NONE);
		toolItem_11.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.editSystemUser();
			}
		});
		toolItem_11.setText("修改");
		
		tableViewer_1 = new TableViewer(composite_9, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table_5 = tableViewer_1.getTable();
		table_5.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table_5.setLinesVisible(true);
		table_5.setHeaderVisible(true);
		table_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TableViewerColumn tableViewerColumn_22 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_15 = tableViewerColumn_22.getColumn();
		tableColumn_15.setWidth(100);
		tableColumn_15.setText("用户名称");
		
		TableViewerColumn tableViewerColumn_23 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_16 = tableViewerColumn_23.getColumn();
		tableColumn_16.setWidth(100);
		tableColumn_16.setText("用户类型");
		
		TableViewerColumn tableViewerColumn_24 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_17 = tableViewerColumn_24.getColumn();
		tableColumn_17.setWidth(121);
		tableColumn_17.setText("创建时间");
		
		TableViewerColumn tableViewerColumn_25 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_18 = tableViewerColumn_25.getColumn();
		tableColumn_18.setWidth(124);
		tableColumn_18.setText("最后修改时间");
		
		TableViewerColumn tableViewerColumn_26 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_19 = tableViewerColumn_26.getColumn();
		tableColumn_19.setWidth(111);
		tableColumn_19.setText("最后修改人");
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setWidth(100);
		tableColumn.setText("备注");
		
		TabItem tabItem_3 = new TabItem(tabFolder, SWT.NONE);
		tabItem_3.setText("设置");
		
		Composite composite_12 = new Composite(tabFolder, SWT.NONE);
		tabItem_3.setControl(composite_12);
		composite_12.setLayout(new GridLayout(2, false));
		
		Group group_4 = new Group(composite_12, SWT.NONE);
		group_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		group_4.setLayout(new GridLayout(3, false));
		GridData gd_group_4 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_group_4.heightHint = 106;
		gd_group_4.widthHint = 461;
		group_4.setLayoutData(gd_group_4);
		group_4.setText("停车场设置");
		
		Button btnCheckButton = new Button(group_4, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.车位满是否允许临时车入场, btnCheckButton.getSelection()+"");
			}
		});
		btnCheckButton.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCheckButton.setText("车位满是否允许临时车入场");
		String string4 = mapSystemSetting.get(SystemSettingTypeEnum.车位满是否允许临时车入场);
		if (string4==null) {
			btnCheckButton.setSelection(Boolean.valueOf(SystemSettingTypeEnum.车位满是否允许临时车入场.getDefaultValue()));
		}else{
			btnCheckButton.setSelection(Boolean.valueOf(string4));
		}
		Button btnCheckButton_1 = new Button(group_4, SWT.CHECK);
		btnCheckButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.车位满是否允许免费车入场, btnCheckButton_1.getSelection()+"");
			}
		});
		btnCheckButton_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnCheckButton_1.setText("车位满是否允许免费车入场");
		
		String string5 = mapSystemSetting.get(SystemSettingTypeEnum.车位满是否允许免费车入场);
		if (string5==null) {
			btnCheckButton_1.setSelection(Boolean.valueOf(SystemSettingTypeEnum.车位满是否允许免费车入场.getDefaultValue()));
		}else{
			btnCheckButton_1.setSelection(Boolean.valueOf(string5));
		}
		
		Button button_3 = new Button(group_4, SWT.CHECK);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.车位满是否允许储值车入场, button_3.getSelection()+"");
			}
		});
		button_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_3.setText("车位满是否允许储值车入场");
		
		String string6 = mapSystemSetting.get(SystemSettingTypeEnum.车位满是否允许储值车入场);
		if (string6==null) {
			button_3.setSelection(Boolean.valueOf(SystemSettingTypeEnum.车位满是否允许储值车入场.getDefaultValue()));
		}else{
			button_3.setSelection(Boolean.valueOf(string6));
		}
		
		Button button_4 = new Button(group_4, SWT.CHECK);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.临时车入场是否确认, button_4.getSelection()+"");
			}
		});
		button_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_4.setText("临时车入场是否确认");
		
		String string7 = mapSystemSetting.get(SystemSettingTypeEnum.临时车入场是否确认);
		if (string7==null) {
			button_4.setSelection(Boolean.valueOf(SystemSettingTypeEnum.临时车入场是否确认.getDefaultValue()));
		}else{
			button_4.setSelection(Boolean.valueOf(string7));
		}
		
		Button button_6 = new Button(group_4, SWT.CHECK);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.临时车零收费是否自动出场, button_6.getSelection()+"");
			}
		});
		button_6.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_6.setText("临时车零收费是否自动出场");
		
		String string8 = mapSystemSetting.get(SystemSettingTypeEnum.临时车零收费是否自动出场);
		if (string8==null) {
			button_6.setSelection(Boolean.valueOf(SystemSettingTypeEnum.临时车零收费是否自动出场.getDefaultValue()));
		}else{
			button_6.setSelection(Boolean.valueOf(string8));
		}
		new Label(group_4, SWT.NONE);
		
		Button button_5 = new Button(group_4, SWT.CHECK);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.固定车入场是否确认, button_5.getSelection()+"");
			}
		});
		button_5.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_5.setText("固定车入场是否确认");
		
		String string9 = mapSystemSetting.get(SystemSettingTypeEnum.固定车入场是否确认);
		if (string9==null) {
			button_5.setSelection(Boolean.valueOf(SystemSettingTypeEnum.固定车入场是否确认.getDefaultValue()));
		}else{
			button_5.setSelection(Boolean.valueOf(string9));
		}
		
		Button button_7 = new Button(group_4, SWT.CHECK);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mapSystemSetting.put(SystemSettingTypeEnum.固定车出场确认, button_7.getSelection()+"");
			}
		});
		button_7.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_7.setText("固定车出场确认");
		
		String string0 = mapSystemSetting.get(SystemSettingTypeEnum.固定车出场确认);
		if (string0==null) {
			button_7.setSelection(Boolean.valueOf(SystemSettingTypeEnum.固定车出场确认.getDefaultValue()));
		}else{
			button_7.setSelection(Boolean.valueOf(string0));
		}
		new Label(group_4, SWT.NONE);
		
		Composite composite_14 = new Composite(group_4, SWT.NONE);
		composite_14.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		composite_14.setLayout(new GridLayout(3, false));
		
		Label label_11 = new Label(composite_14, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_11.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_11.setText("数据库备份位置");
		
		text_setting_dataBaseSave = new Text(composite_14, SWT.BORDER);
		text_setting_dataBaseSave.setEditable(false);
		text_setting_dataBaseSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(shell, SWT.SINGLE);
				fileDialog.setText("请选择路径");
				String open = fileDialog.open();
				if (StrUtil.isEmpty(open)) {
					return;
				}
				text_setting_dataBaseSave.setText(open);
				mapSystemSetting.put(SystemSettingTypeEnum.数据库备份位置, open);
			}
		});
		text_setting_dataBaseSave.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		GridData gd_text_setting_dataBaseSave = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_setting_dataBaseSave.widthHint = 239;
		text_setting_dataBaseSave.setLayoutData(gd_text_setting_dataBaseSave);
		String string = mapSystemSetting.get(SystemSettingTypeEnum.数据库备份位置);
		text_setting_dataBaseSave.setText(string==null?SystemSettingTypeEnum.数据库备份位置.getDefaultValue():string);
		
		Button button_8 = new Button(composite_14, SWT.NONE);
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.backup(text_setting_dataBaseSave.getText());
				
			}
		});
		button_8.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_8.setText("备份");
		
		Composite composite_15 = new Composite(group_4, SWT.NONE);
		composite_15.setLayout(new GridLayout(4, false));
		composite_15.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label label_12 = new Label(composite_15, SWT.NONE);
		label_12.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		GridData gd_label_12 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_12.widthHint = 108;
		label_12.setLayoutData(gd_label_12);
		label_12.setText("图片存放位置");
		
		text_setting_imgSave = new Text(composite_15, SWT.BORDER);
		text_setting_imgSave.setEditable(false);
		text_setting_imgSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				DirectoryDialog directoryDialog=new DirectoryDialog(shell,SWT.SINGLE);
				String open = directoryDialog.open();
				if (StrUtil.isEmpty(open)) {
					return;
				}
				text_setting_imgSave.setText(open);
				mapSystemSetting.put(SystemSettingTypeEnum.图片保存位置, open);
//				presenter.setting();
			}
		});
		text_setting_imgSave.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		GridData gd_text_setting_imgSave = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_setting_imgSave.widthHint = 241;
		text_setting_imgSave.setLayoutData(gd_text_setting_imgSave);
		String string2 = mapSystemSetting.get(SystemSettingTypeEnum.图片保存位置);
		text_setting_imgSave.setText(string2==null?SystemSettingTypeEnum.图片保存位置.getDefaultValue():string2);
		
		Button button_9 = new Button(composite_15, SWT.CHECK);
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!button_9.getSelection()) {
					mapSystemSetting.put(SystemSettingTypeEnum.图片保存多少天, null);
					return;
				}
				String text = text_setting_imgSaveDays.getText();
				Integer valueOf=0;
				try {
					valueOf = Integer.valueOf(text);
				} catch (NumberFormatException e1) {
					
				}
				mapSystemSetting.put(SystemSettingTypeEnum.图片保存多少天, valueOf+"");
			}
		});
		button_9.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_9.setText("保存多少天的照片");
		String string3 = mapSystemSetting.get(SystemSettingTypeEnum.图片保存多少天);
		
		text_setting_imgSaveDays = new Text(composite_15, SWT.BORDER);
		text_setting_imgSaveDays.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_setting_imgSaveDays.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (string3==null) {
			button_9.setSelection(false);
			text_setting_imgSaveDays.setText(SystemSettingTypeEnum.图片保存多少天.getDefaultValue());
		}else{
			if (string3.equals(SystemSettingTypeEnum.图片保存多少天.getDefaultValue())) {
				
			}else
			button_9.setSelection(true);
			text_setting_imgSaveDays.setText(string3);
		}
		
		Button button_12 = new Button(group_4, SWT.NONE);
		button_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.saveAllSystemSetting();
			}
		});
		button_12.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		button_12.setText("保存设置");
		new Label(group_4, SWT.NONE);
		new Label(group_4, SWT.NONE);
		new Label(group_4, SWT.NONE);
		new Label(group_4, SWT.NONE);
		new Label(group_4, SWT.NONE);
		
		Composite composite_13 = new Composite(composite_12, SWT.NONE);
		composite_13.setLayout(new GridLayout(2, false));
		GridData gd_composite_13 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_composite_13.heightHint = 360;
		composite_13.setLayoutData(gd_composite_13);
		
		Label label_8 = new Label(composite_13, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_8.setText("黑名单");
		
		ToolBar toolBar_4 = new ToolBar(composite_13, SWT.FLAT | SWT.RIGHT);
		toolBar_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		ToolItem toolItem_12 = new ToolItem(toolBar_4, SWT.NONE);
		toolItem_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddBlackUserWizard v=new AddBlackUserWizard(this);
				commonui.showWizard(v);
			}
		});
		toolItem_12.setText("添加");
		
		ToolItem toolItem_13 = new ToolItem(toolBar_4, SWT.NONE);
		toolItem_13.setText("删除");
		
		TableViewer tableViewer_6 = new TableViewer(composite_13, SWT.BORDER | SWT.FULL_SELECTION);
		table_6 = tableViewer_6.getTable();
		table_6.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		table_6.setLinesVisible(true);
		table_6.setHeaderVisible(true);
		GridData gd_table_6 = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_table_6.widthHint = 170;
		table_6.setLayoutData(gd_table_6);
		
		TableViewerColumn tableViewerColumn_30 = new TableViewerColumn(tableViewer_6, SWT.NONE);
		TableColumn tableColumn_24 = tableViewerColumn_30.getColumn();
		tableColumn_24.setAlignment(SWT.CENTER);
		tableColumn_24.setWidth(100);
		tableColumn_24.setText("车牌");
		
		TabItem tabItem_4 = new TabItem(tabFolder, SWT.NONE);
		tabItem_4.setText("关于");
		
		Composite composite_10 = new Composite(tabFolder, SWT.NONE);
		tabItem_4.setControl(composite_10);
		composite_10.setLayout(new GridLayout(1, false));
		Composite composite1 = new Composite(composite_10, SWT.BORDER);
		GridData gd_composite1 = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gd_composite1.widthHint = 375;
		composite1.setLayoutData(gd_composite1);
		composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gl_composite = new GridLayout(1, true);
		gl_composite.verticalSpacing = 0;
		gl_composite.marginWidth = 0;
		gl_composite.horizontalSpacing = 0;
		gl_composite.marginHeight = 0;
		composite1.setLayout(gl_composite);
		
		Composite composite_11 = new Composite(composite1, SWT.NONE);
		composite_11.setBackgroundImage(JFaceUtil.getImage("donglu"));
		composite_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_11.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Composite composite_21 = new Composite(composite1, SWT.NONE);
		composite_21.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginLeft = 30;
		gl_composite_2.marginTop = 10;
		gl_composite_2.verticalSpacing = 10;
		composite_21.setLayout(gl_composite_2);
		composite_21.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblNewLabel_11 = new Label(composite_21, SWT.NONE);
		lblNewLabel_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_11.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_11.setFont(SWTResourceManager.getFont("宋体", 9, SWT.NORMAL));
		lblNewLabel_11.setText("软件名称：");
		
		lblNewLabel_2 = new Label(composite_21, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("宋体", 9, SWT.NORMAL));
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setText("东陆一卡通管理平台");
		
		Label label1 = new Label(composite_21, SWT.NONE);
		label1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label1.setText("开发组织：");
		
		label_1 = new Label(composite_21, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("深圳市东陆高新实业有限公司");
		
		Label label_21 = new Label(composite_21, SWT.NONE);
		label_21.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_21.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_21.setText("软件版本：");
		
		lblNewLabel_3 = new Label(composite_21, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setText(System.getProperty("version","读取失败"));
		
		Label label_31 = new Label(composite_21, SWT.NONE);
		label_31.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_31.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_31.setText("数据库版本：");
		
		Label label_4 = new Label(composite_21, SWT.NONE);
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_4.setText("1.2.0.1");
		
		Label label_51 = new Label(composite_21, SWT.NONE);
		label_51.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_51.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_51.setText("发布时间：");
		
		Label label_6 = new Label(composite_21, SWT.NONE);
		label_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_6.setText("2014-08-15 15：00：00");
		new Label(composite_21, SWT.NONE);
		new Label(composite_21, SWT.NONE);
		
		Composite composite_31 = new Composite(composite1, SWT.NONE);
		composite_31.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_31.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite_3 = new GridData(GridData.FILL_HORIZONTAL);
		gd_composite_3.horizontalAlignment = SWT.RIGHT;
		composite_31.setLayoutData(gd_composite_3);
		
		Image handImg = JFaceUtil.getImage("hand_16");
		CLabel lblNewLabel1 = new CLabel(composite_31, SWT.NONE);
		lblNewLabel1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel1.setText("首页");
		lblNewLabel1.setImage(JFaceUtil.getImage("home_32"));
		lblNewLabel1.setCursor(new org.eclipse.swt.graphics.Cursor(shell.getDisplay(),handImg.getImageData(),0,0));
		
		CLabel lblNewLabel2 = new CLabel(composite_31, SWT.NONE);
		lblNewLabel2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel2.setText("邮箱");
		lblNewLabel2.setImage(JFaceUtil.getImage("email_32"));
		lblNewLabel2.setCursor(new org.eclipse.swt.graphics.Cursor(shell.getDisplay(),handImg.getImageData(),0,0));

		lblNewLabel1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				try{
					Runtime.getRuntime().exec("cmd /k start "+"http://www.dongluhitec.com/");
				}catch(Exception ex){}
			}
			
		});
		
		lblNewLabel2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				try{
					Runtime.getRuntime().exec("cmd /k start "+"mailto:154341736@qq.com");
				}catch(Exception ex){}
			}
			
		});
		
		controlDispay();
		m_bindingContext = initDataBindings();
	}

	private void controlDispay() {
		String type = System.getProperty("userType");
		if (type==null) {
			System.exit(0);
		}
		if (!type.equals("系统管理员")) {
			carparkConfigToolBar.dispose();
		}
		
	}

	@Override
	public void disponse() {
		this.shell.dispose();
		
	}

	@Override
	public void setShell(Shell shell) {
		this.shell=shell;
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		BeansListObservableFactory treeObservableFactory = new BeansListObservableFactory(SingleCarparkCarpark.class, "childs");
		TreeBeanAdvisor treeAdvisor = new TreeBeanAdvisor(SingleCarparkCarpark.class, "parent", "childs", null);
		ObservableListTreeContentProvider treeContentProvider = new ObservableListTreeContentProvider(treeObservableFactory, treeAdvisor);
		treeViewer.setLabelProvider(new TreeObservableLabelProvider(treeContentProvider.getKnownElements(), SingleCarparkCarpark.class, "name", null));
		treeViewer.setContentProvider(treeContentProvider);
		//
		IObservableList listCarparkCarparkModelObserveList = BeanProperties.list("listCarpark").observe(carparkModel);
		treeViewer.setInput(listCarparkCarparkModelObserveList);
		//
		IObservableValue observeSingleSelectionTreeViewer = ViewerProperties.singleSelection().observe(treeViewer);
		IObservableValue carparkCarparkModelObserveValue = BeanProperties.value("carpark").observe(carparkModel);
		bindingContext.bindValue(observeSingleSelectionTreeViewer, carparkCarparkModelObserveValue, null, null);
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), SingleCarparkUser.class, new String[]{"id", "name", "plateNo", "address", "type", "validTo", "carparkNo", "remark"});
		tableViewer_user.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer_user.setContentProvider(listContentProvider);
		//
		IObservableList allListUserModelObserveList = BeanProperties.list("allList").observe(userModel);
		tableViewer_user.setInput(allListUserModelObserveList);
		//
		IObservableList observeMultiSelectionTableViewer_user = ViewerProperties.multipleSelection().observe(tableViewer_user);
		IObservableList selectListUserModelObserveList = BeanProperties.list("selectList").observe(userModel);
		bindingContext.bindList(observeMultiSelectionTableViewer_user, selectListUserModelObserveList, null, null);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_1 = BeansObservables.observeMaps(listContentProvider_1.getKnownElements(), SingleCarparkSystemUser.class, new String[]{"userName", "type", "createDate", "lastEditDate", "lastEditUser", "remark"});
		tableViewer_1.setLabelProvider(new ObservableMapLabelProvider(observeMaps_1));
		tableViewer_1.setContentProvider(listContentProvider_1);
		//
		IObservableList listSystemUserModelObserveList = BeanProperties.list("list").observe(systemUserModel);
		tableViewer_1.setInput(listSystemUserModelObserveList);
		//
		IObservableList observeMultiSelectionTableViewer_1 = ViewerProperties.multipleSelection().observe(tableViewer_1);
		IObservableList selectListSystemUserModelObserveList = BeanProperties.list("selectList").observe(systemUserModel);
		bindingContext.bindList(observeMultiSelectionTableViewer_1, selectListSystemUserModelObserveList, null, null);
		//
		ObservableListContentProvider listContentProvider_2 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_2 = BeansObservables.observeMaps(listContentProvider_2.getKnownElements(), CarparkChargeInfo.class, new String[]{"code", "name", "type"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_2));
		tableViewer.setContentProvider(listContentProvider_2);
		//
		IObservableList listCarparkChargeCarparkModelObserveList = BeanProperties.list("listCarparkCharge").observe(carparkModel);
		tableViewer.setInput(listCarparkChargeCarparkModelObserveList);
		//
		IObservableValue observeSingleSelectionTableViewer = ViewerProperties.singleSelection().observe(tableViewer);
		IObservableValue carparkChargeInfoCarparkModelObserveValue = BeanProperties.value("carparkChargeInfo").observe(carparkModel);
		bindingContext.bindValue(observeSingleSelectionTableViewer, carparkChargeInfoCarparkModelObserveValue, null, null);
		//
		ObservableListContentProvider listContentProvider_3 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_3 = BeansObservables.observeMaps(listContentProvider_3.getKnownElements(), SingleCarparkInOutHistory.class, new String[]{"plateNo", "userName", "carType", "inDevice", "inTime", "outDevice", "outTime", "operaName", "shouldMoney", "factMoney", "freeMoney", "returnAccount"});
		tableViewer_2.setLabelProvider(new ObservableMapLabelProvider(observeMaps_3));
		tableViewer_2.setContentProvider(listContentProvider_3);
		//
		IObservableList listSearchInOutHistoryModelObserveList = BeanProperties.list("listSearch").observe(inOutHistoryModel);
		tableViewer_2.setInput(listSearchInOutHistoryModelObserveList);
		//
		IObservableValue observeTextLabel_inout_nowCountObserveWidget = WidgetProperties.text().observe(label_inout_nowCount);
		IObservableValue countSearchInOutHistoryModelObserveValue = BeanProperties.value("countSearch").observe(inOutHistoryModel);
		bindingContext.bindValue(observeTextLabel_inout_nowCountObserveWidget, countSearchInOutHistoryModelObserveValue, null, null);
		//
		IObservableValue observeTextLabel_inout_totalCountObserveWidget = WidgetProperties.text().observe(label_inout_totalCount);
		IObservableValue countSearchAllInOutHistoryModelObserveValue = BeanProperties.value("countSearchAll").observe(inOutHistoryModel);
		bindingContext.bindValue(observeTextLabel_inout_totalCountObserveWidget, countSearchAllInOutHistoryModelObserveValue, null, null);
		//
		return bindingContext;
	}

	public Map<SystemSettingTypeEnum, String> getMapSystemSetting() {
		return mapSystemSetting;
	}
}
