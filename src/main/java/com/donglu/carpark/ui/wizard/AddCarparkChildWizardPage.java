package com.donglu.carpark.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkCarpark;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.wb.swt.SWTResourceManager;

public class AddCarparkChildWizardPage extends WizardPage {
	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;
	private Text text;
	private Text text_1;
	private SingleCarparkCarpark model;
	private Button btnBu;
	private Button button;
	/**
	 * Create the wizard.
	 * @param model 
	 */
	public AddCarparkChildWizardPage(SingleCarparkCarpark model) {
		super("wizardPage");
		this.model=model;
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(container, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		composite.setLayout(new GridLayout(2, false));
		
		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("停车场编码");
		
		text = new Text(composite, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("停车场名称");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		
		btnBu = new Button(composite, SWT.CHECK);
		btnBu.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		btnBu.setText("不允许临时车进入");
		new Label(composite, SWT.NONE);
		
		button = new Button(composite, SWT.CHECK);
		button.setText("是否收费");
		button.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue codeModelObserveValue = BeanProperties.value("code").observe(model);
		bindingContext.bindValue(observeTextTextObserveWidget, codeModelObserveValue, null, null);
		//
		IObservableValue observeTextText_1ObserveWidget = WidgetProperties.text(SWT.Modify).observe(text_1);
		IObservableValue nameModelObserveValue = BeanProperties.value("name").observe(model);
		bindingContext.bindValue(observeTextText_1ObserveWidget, nameModelObserveValue, null, null);
		//
		IObservableValue observeSelectionButtonObserveWidget = WidgetProperties.selection().observe(btnBu);
		IObservableValue tempCarIsInModelObserveValue = BeanProperties.value("tempCarIsIn").observe(model);
		bindingContext.bindValue(observeSelectionButtonObserveWidget, tempCarIsInModelObserveValue, null, null);
		//
		IObservableValue observeSelectionButtonObserveWidget_1 = WidgetProperties.selection().observe(button);
		IObservableValue isChargeModelObserveValue = BeanProperties.value("isCharge").observe(model);
		bindingContext.bindValue(observeSelectionButtonObserveWidget_1, isChargeModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
