package kr.ezen.project_zzbs.domain.enumClass;

public enum BoardCategory {
    FREE, NOOB, GENI;;

    public static BoardCategory of(String category) {
        if (category.equalsIgnoreCase("free")) return BoardCategory.FREE;
        else if (category.equalsIgnoreCase("noob")) return BoardCategory.NOOB;
        else if (category.equalsIgnoreCase("Geni")) return BoardCategory.GENI;
        else return null;
    }
}
