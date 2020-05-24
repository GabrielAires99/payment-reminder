package model.entities;

import java.util.Calendar;
import java.util.Date;

import model.entities.enums.Frequency;

public class Bill {

	private Integer id;
	private String description;
	private Date dueDate;
	private Frequency frequency;

	private Payer payer;
	private Payee payee;

	public Bill() {
	}

	public Bill(Integer id, String description, Date dueDate, Frequency frequency, Payer payer, Payee payee) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.frequency = frequency;
		this.payer = payer;
		this.payee = payee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public Payer getPayer() {
		return payer;
	}

	public void setPayer(Payer payer) {
		this.payer = payer;
	}

	public Payee getPayee() {
		return payee;
	}

	public void setPayee(Payee payee) {
		this.payee = payee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void updateDueDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dueDate);
		if (frequency == Frequency.WEEKLY) {
			cal.add(Calendar.WEEK_OF_MONTH, 1);
		} else if (frequency == Frequency.MONTHLY) {
			cal.add(Calendar.MONTH, 1);
		} else {
			cal.add(Calendar.YEAR, 1);
		}
		dueDate = cal.getTime();
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", description=" + description + ", dueDate=" + dueDate + ", frequency=" + frequency
				+ ", payer=" + payer + ", payee=" + payee + "]";
	}
}
