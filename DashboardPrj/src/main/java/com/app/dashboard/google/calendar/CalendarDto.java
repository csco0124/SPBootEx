package com.app.dashboard.google.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarDto {
	private String summary;		//회의명
	private String location;	//회의장소
	private LocalDateTime start_datetime;	//시작일
	private LocalDateTime end_datetime;		//종료일
	private String start_datetime_str;		//시작일 문자열
	
	private String end_datetime_str;		//종료일 문자열
	private List<Attendees> AttendeesList;	//참석자 리스트
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDateTime getStart_datetime() {
		return start_datetime;
	}
	public void setStart_datetime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		LocalDateTime start_datetime = LocalDateTime.parse(date, formatter);
		this.start_datetime = start_datetime;
		this.start_datetime_str = start_datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	public LocalDateTime getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		LocalDateTime end_datetime = LocalDateTime.parse(date, formatter);
		this.end_datetime = end_datetime;
		this.end_datetime_str = end_datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	public String getStart_datetime_str() {
		return start_datetime_str;
	}
	public String getEnd_datetime_str() {
		return end_datetime_str;
	}
	public List<Attendees> getAttendeesList() {
		return AttendeesList;
	}
	public void setAttendeesList(List<Attendees> attendeesList) {
		AttendeesList = attendeesList;
	}

	public class Attendees {
		private String displayName;	//참석자
		private String email;		//참석자 이메일
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
}
