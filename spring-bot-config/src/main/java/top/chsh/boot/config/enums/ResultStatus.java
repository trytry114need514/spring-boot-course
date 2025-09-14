package top.chsh.boot.config.enums;

public enum ResultStatus {
    SUCCESS("发送成功"), FAIL("发送失败");

    private final String info;

    ResultStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
