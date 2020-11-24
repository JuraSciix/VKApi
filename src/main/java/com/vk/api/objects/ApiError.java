package com.vk.api.objects;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ApiError {

    @SerializedName("error_code")
    public int code;

    @SerializedName("error_msg")
    public String message;

    @SerializedName("request_params")
    public List<RequestParam> requestParams;

    @SerializedName("captcha_sid")
    public long captchaSid;

    @SerializedName("captcha_img")
    public URL captchaImage;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

    public long getCaptchaSid() {
        return captchaSid;
    }

    public void setCaptchaSid(long captchaSid) {
        this.captchaSid = captchaSid;
    }

    public URL getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(URL captchaImage) {
        this.captchaImage = captchaImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiError)) {
            return false;
        }
        ApiError a = (ApiError) o;
        return code == a.code &&
                Objects.equals(message, a.message) &&
                Objects.equals(requestParams, a.requestParams) &&
                captchaSid == a.captchaSid &&
                Objects.equals(captchaImage, a.captchaImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, requestParams, captchaSid, captchaImage);
    }
}
