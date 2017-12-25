package noteapp.jc.com.quicknotes;

/**
 * Created by Jayjeet Chakraborty on 08-12-2017.
 */

public class CustomList {

    String titleStore,noteStore,memoStore,dateTime;

    public CustomList(String titleStore, String noteStore, String memoStore, String dateTime) {
        this.titleStore = titleStore;
        this.noteStore = noteStore;
        this.memoStore = memoStore;
        this.dateTime = dateTime;//code additions
    }

    public String getTitleStore() {
        return titleStore;
    }

    public void setTitleStore(String titleStore) {
        this.titleStore = titleStore;
    }

    public String getNoteStore() {
        return noteStore;
    }

    public void setNoteStore(String noteStore) {
        this.noteStore = noteStore;
    }

    public String getMemoStore() {
        return memoStore;
    }

    public void setMemoStore(String memoStore) {
        this.memoStore = memoStore;
    }

    public String getDateTime() {
        return dateTime;
    }////code additions

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }//code additions
}
