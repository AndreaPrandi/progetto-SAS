package persistence;

import businesslogic.kitchenTask.KitchenShift;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.ServiceSheet;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {

    @Override
    public void updateKitchenTaskDeleted(ServiceSheet sheet, KitchenTask task) {
        KitchenTask.deleteKitchenTask(sheet, task);
    }

    @Override
    public void updateKitchenTasksRearranged(ServiceSheet sheet) {
        ServiceSheet.saveKitchenTasksOrder(sheet);
    }

    @Override
    public void updateKitchenTaskAdded(ServiceSheet sheet, KitchenTask task) {
        KitchenTask.saveNewKitchenTask(sheet.getId(), task, sheet.getAllTasks().indexOf(task));
    }

    @Override
    public void updateKitchenTaskReassigned(KitchenTask task) {
        KitchenTask.updateTaskReassigned(task);
    }

    @Override
    public void updateKitchenTaskUpdated(KitchenTask task) {
        KitchenTask.updateKitchenTask(task);
    }

    @Override
    public void updateKitchenShiftUpdated(KitchenShift shift) {
        KitchenShift.updateKitchenShift(shift);
    }

    @Override
    public void updateServiceSheetCreated(ServiceSheet sheet) {
        ServiceSheet.saveNewServiceSheet(sheet);
    }

    @Override
    public void updateKitchenTaskReset(ServiceSheet sheet, KitchenTask task) {
        KitchenTask.saveNewKitchenTask(sheet.getId(), task, sheet.getAllTasks().indexOf(task));
    }
}
