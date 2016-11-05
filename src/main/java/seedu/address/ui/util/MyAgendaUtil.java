package seedu.address.ui.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskOccurrence;
//@@author A0147967J
/**
 * A utility class for handling agenda appointments 
 * and LocalDateTime conversion. 
 */
public class MyAgendaUtil {
    
    /** Returns the startTime of the appointment. */
    public static LocalDateTime getAppointmentStartTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getStartLocalDateTime().getHour())
                .plusMinutes(source.getStartLocalDateTime().getMinute());
    }

    /** Returns the endTime of the appointment. */
    public static LocalDateTime getAppointmentEndTime(LocalDateTime startPoint, int dayOffset, AppointmentImplLocal source) {
        return startPoint.plusDays(dayOffset).plusHours(source.getEndLocalDateTime().getHour())
                .plusMinutes(source.getEndLocalDateTime().getMinute());
    }

    
    /** Returns a LocalDateTime object converted from TaskDate. */
    public static LocalDateTime getConvertedTime(TaskDate t) {
        return LocalDateTime.ofInstant(new Date(t.getDateInLong()).toInstant(), ZoneId.systemDefault());
    }
    
    /** Returns an AppointmentImplLocal object from a task component */
    public static AppointmentImplLocal getAppointment(TaskOccurrence taskOccurrence) {

        AppointmentImplLocal appointment = new AppointmentImplLocal();
        appointment.setSummary(taskOccurrence.getTaskReference().getName().fullName);
        appointment.setDescription(taskOccurrence.getTaskReference().tagsString());
        appointment.setStartLocalDateTime(getConvertedTime(taskOccurrence.getStartDate()));
        appointment.setEndLocalDateTime(getConvertedTime(taskOccurrence.getEndDate()));
        if (taskOccurrence.isArchived()) {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("archive"));
        } else if (taskOccurrence.getTaskReference().isBlockedSlot()) {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("block"));
        } else {
            appointment.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        }
        return appointment;

    }
    
    /**
     * Returns a new appointment with start and end time specified and contains
     * same data with source.
     */
    public static AppointmentImplLocal copyAppointment(AppointmentImplLocal src, LocalDateTime start, LocalDateTime end) {
        AppointmentImplLocal newOne = new AppointmentImplLocal().withAppointmentGroup(src.getAppointmentGroup())
                .withDescription(src.getDescription()).withSummary(src.getSummary());

        newOne.setStartLocalDateTime(start);
        newOne.setEndLocalDateTime(end);
        if (start.isAfter(src.getStartLocalDateTime()))
            newOne.setAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("normal"));
        return newOne;
    }
    
    /**
     * Returns true if the two appointment is considered as same. 
     */
    public static boolean isSameAppointment(Appointment a, AppointmentImplLocal a2){
        return a.getAppointmentGroup().getStyleClass().equals(a2.getAppointmentGroup().getStyleClass()) 
                && a.getStartLocalDateTime().equals(a2.getStartLocalDateTime())
                && a.getEndLocalDateTime().equals(a2.getEndLocalDateTime())
                && a.getDescription().equals(a2.getDescription())
                && a.getSummary().equals(a2.getSummary());
    }
    
    /**
     * Gets the end boundary of the recurring task with its recurring period specified.
     */
    public static LocalDateTime getEndBoundary(TaskOccurrence taskOccurrence, AppointmentImplLocal appointment) {
        LocalDateTime endBoundary = null;
        int recurringCount = taskOccurrence.getTaskReference().getRecurringPeriod();
        if(recurringCount >= 0){
            switch (taskOccurrence.getTaskReference().getRecurringType()) {
            case YEARLY:
                endBoundary = appointment.getStartLocalDateTime().plusYears(recurringCount);
                break;
            case MONTHLY:
                endBoundary = appointment.getStartLocalDateTime().plusMonths(recurringCount);
                break;
            case WEEKLY:
                endBoundary = appointment.getStartLocalDateTime().plusWeeks(recurringCount);
                break;
            case DAILY:
                endBoundary = appointment.getStartLocalDateTime().plusDays(recurringCount);
                break;
            default:
                break;
            }
        }
        return endBoundary;
    }
}
