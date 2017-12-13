package com.virgo.financeloan.util;

import android.text.TextUtils;

/**
 * Created by etiennelawlor on 6/1/15.
 */
public class Span {

    // region Member Variables
    private String Text;
    private Integer ForegroundColor;
    private Integer BackgroundColor;
    private Float RelativeSize;
    private Integer AbsoluteSize;
    private android.graphics.Typeface Typeface;
    private Boolean IsUrl;
    private Boolean IsUnderline;
    private Boolean IsStrikethru;
    private Integer QuoteColor;
    private Boolean IsSubscript;
    private Boolean IsSuperscript;
    private String Regex;
    private android.text.style.ClickableSpan ClickableSpan;
    private Float ScaleX;

    // endregion

    // region Constructor
    private Span(Builder builder) {
        Text = builder.Text;
        ForegroundColor = builder.ForegroundColor;
        BackgroundColor = builder.BackgroundColor;
        Typeface = builder.Typeface;
        RelativeSize = builder.RelativeSize;
        AbsoluteSize = builder.AbsoluteSize;
        IsUrl = builder.IsUrl;
        IsUnderline = builder.IsUnderline;
        IsStrikethru = builder.IsStrikethru;
        QuoteColor = builder.QuoteColor;
        IsSubscript = builder.IsSubscript;
        IsSuperscript = builder.IsSuperscript;
        Regex = builder.Regex;
        ClickableSpan = builder.ClickableSpan;
        ScaleX = builder.ScaleX;
    }
    // endregion

    // region Getters
    public String getText() {
        if (TextUtils.isEmpty(Text)) {
            return "";
        } else {
            return Text;
        }
    }

    public Integer getForegroundColor() {
        if (ForegroundColor == null)
            return -1;
        else
            return ForegroundColor;
    }

    public Integer getBackgroundColor() {
        if (BackgroundColor == null)
            return -1;
        else
            return BackgroundColor;
    }

    public android.graphics.Typeface getTypeface() {
        return Typeface;
    }

    public Float getRelativeSize() {
        if (RelativeSize == null)
            return 0.0F;
        else
            return RelativeSize;
    }

    public Integer getAbsoluteSize() {
        if (AbsoluteSize == null)
            return -1;
        else
            return AbsoluteSize;
    }

    public Boolean isUrl() {
        if (IsUrl == null)
            return false;
        else
            return IsUrl;
    }

    public Boolean isUnderline() {
        if (IsUnderline == null)
            return false;
        else
            return IsUnderline;
    }

    public Boolean isStrikethru() {
        if (IsStrikethru == null)
            return false;
        else
            return IsStrikethru;
    }

    public Integer getQuoteColor() {
        if (QuoteColor == null)
            return -1;
        else
            return QuoteColor;
    }

    public Boolean isSubscript() {
        if (IsSubscript == null)
            return false;
        else
            return IsSubscript;
    }

    public Boolean isSuperscript() {
        if (IsSuperscript == null)
            return false;
        else
            return IsSuperscript;
    }

    public String getRegex() {
        if (TextUtils.isEmpty(Regex)) {
            return "";
        } else {
            return Regex;
        }
    }

    public android.text.style.ClickableSpan getClickableSpan() {
        return ClickableSpan;
    }

    public Float getScaleX() {
        if (ScaleX == null)
            return 0.0F;
        else
            return ScaleX;
    }

    // endregion

    // region Builder class

    public static class Builder {
        // Required parameters
        private final String Text;

        // Optional parameters - initialized to default values
        private Integer ForegroundColor = 0;
        private Integer BackgroundColor = 0;
        private android.graphics.Typeface Typeface = null;
        private Float RelativeSize = 0.0F;
        private Integer AbsoluteSize = 0;
        private Boolean IsUrl = false;
        private Boolean IsUnderline = false;
        private Boolean IsStrikethru = false;
        private Integer QuoteColor = 0;
        private Boolean IsSubscript = false;
        private Boolean IsSuperscript = false;
        private String Regex = "";
        private android.text.style.ClickableSpan ClickableSpan = null;
        private Float ScaleX = 0.0F;

        public Builder(String text) {
            this.Text = text;
        }

        public Builder foregroundColor(Integer fgColor) {
            ForegroundColor = fgColor;
            return this;
        }

        public Builder backgroundColor(Integer bgColor) {
            BackgroundColor = bgColor;
            return this;
        }

        public Builder typeface(android.graphics.Typeface typeface) {
            Typeface = typeface;
            return this;
        }

        public Builder relativeSize(Float relativeSize) {
            RelativeSize = relativeSize;
            return this;
        }

        public Builder absoluteSize(Integer absoluteSize) {
            AbsoluteSize = absoluteSize;
            return this;
        }

        public Builder isUrl(Boolean isUrl) {
            IsUrl = isUrl;
            return this;
        }

        public Builder isUnderline(Boolean isUnderline) {
            IsUnderline = isUnderline;
            return this;
        }

        public Builder isStrikethru(Boolean isStrikethru) {
            IsStrikethru = isStrikethru;
            return this;
        }

        public Builder quoteColor(Integer quoteColor) {
            QuoteColor = quoteColor;
            return this;
        }

        public Builder subscript(Boolean isSubscript) {
            IsSubscript = isSubscript;
            return this;
        }

        public Builder superscript(Boolean isSuperscript) {
            IsSuperscript = isSuperscript;
            return this;
        }

        public Builder regex(String regex) {
            Regex = regex;
            return this;
        }

        public Builder clickableSpan(android.text.style.ClickableSpan clickableSpan) {
            ClickableSpan = clickableSpan;
            return this;
        }

        public Builder scaleX(Float scaleX) {
            ScaleX = scaleX;
            return this;
        }

        public Span build() {
            return new Span(this);
        }
    }
    // endregion

}
