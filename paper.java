public class paper {
    private int month;
    private int year;

    // HL = 4; HLSL = 3; SL = 2; ab_initio = 1
    private int level;
    private int paper;
    private String name;
    private boolean mrkScheme;

    public int getYear() { return year; }

    public int getMonth() { return month; }

    public int getLevel() { return level; }

    public String getName() { return name; }

    public int getPaper() { return paper; } 

    public boolean isMrkScheme() { return mrkScheme; }

    //This year thing will only last until 2099 when it becomes 2100 it will no longer find the year
    //This month thing will only last if the tests are only in Nov and May
    public paper(String FILEName){
        FILEName = FILEName.toLowerCase();

        year = Integer.parseInt(FILEName.substring(FILEName.indexOf("20"), FILEName.indexOf("20") + 4));

        if(FILEName.indexOf("nov") != -1) month = 1;
        else if(FILEName.indexOf("may") != -1) month = 0;
        else System.out.println("COULDN'T FIND MONTH ERROR");

        name = FILEName;

        if(FILEName.indexOf("ab_initio") != -1) level = 1;
        else if(FILEName.indexOf("hlsl") != -1) level = 3;
        else if(FILEName.indexOf("slhl") != -1) level = 3;
        else if(FILEName.indexOf("sl") != -1) level = 2;
        else if (FILEName.indexOf("hl") != -1) level = 4;
        else throw new NullPointerException("ERROR: PAPER LEVER WAS NOT FOUND");

        if(FILEName.indexOf("paper_1") != -1 || FILEName.indexOf("p1") != -1) paper = 1;
        else if(FILEName.indexOf("paper_2") != -1 || FILEName.indexOf("p2") != -1) paper = 2;
        else if(FILEName.indexOf("paper_3") != -1 || FILEName.indexOf("p3") != -1) paper = 3;

        if(FILEName.indexOf("markscheme") != -1 || FILEName.indexOf("marks") != -1) mrkScheme = true;
        else mrkScheme = false;

    }

    public boolean isLater(paper paper1){
        if(this.year != paper1.getYear()) return this.year > paper1.getYear();
        else if(this.month != paper1.getMonth()) return this.month > paper1.getMonth();
        else if(this.level != paper1.getLevel()) return this.level > paper1.getLevel();
        return otherIsMrkScheme(paper1);
    }

    public boolean otherIsMrkScheme(paper p){
        //Returns true if the other paper is a markscheme of the current paper
        if (p.getYear() == this.year && p.getMonth() == this.month && p.getPaper() == this.paper && p.getLevel() == this.level && p.isMrkScheme() && !this.mrkScheme) return true;
        return false;
    }

    public String rename(String subject){
        String str_month = "Nov";
        if(month == 0) str_month = "May";

        String str_level = "HL";
        if(level == 1) str_level = "ab_initio";
        else if(level == 2) str_level = "SL";
        else if(level == 3) str_level = "SLHL";

        String str_paper = "Paper_3";
        if(paper == 1) str_paper = "Paper_1";
        else if(paper == 2) str_paper = "Paper_2";

        return year + str_month + "_" + subject + "_" + str_level + "_"+ str_paper;
    }

}
