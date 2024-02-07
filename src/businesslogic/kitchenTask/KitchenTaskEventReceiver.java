package businesslogic.kitchenTask;

public interface KitchenTaskEventReceiver {
    void updateServiceSheetCreated(ServiceSheet sheet);
    void updateKitchenTaskReset(ServiceSheet sheet, KitchenTask task);
    void updateKitchenTaskDeleted(ServiceSheet sheet, KitchenTask task);
    void updateKitchenTasksRearranged(ServiceSheet sheet);
    void updateKitchenTaskAdded(ServiceSheet sheet, KitchenTask task);
    void updateKitchenTaskReassigned(KitchenTask task);
    void updateKitchenTaskUpdated(KitchenTask task);
    void updateKitchenShiftUpdated(KitchenShift shift);
}
