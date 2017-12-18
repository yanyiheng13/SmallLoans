package com.virgo.financeloan.ui;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/18 17:48
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class FileEnums {
    public FileEnums() {
    }

    public static enum MotorAccidentStatusEnum {
        YES(Integer.valueOf(0), "否"),
        NO(Integer.valueOf(1), "是");

        public final Integer code;
        public final String desc;

        private MotorAccidentStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.MotorAccidentStatusEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.MotorAccidentStatusEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.MotorAccidentStatusEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum MortgageStatusEnum {
        IN_STORAGE(Integer.valueOf(0), "已入库"),
        BORROWING(Integer.valueOf(1), "借阅中"),
        OUT_STORAGE(Integer.valueOf(2), "已出库");

        public final Integer code;
        public final String desc;

        private MortgageStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.MortgageStatusEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.MortgageStatusEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.MortgageStatusEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum MortgageTypeEnum {
        HOUSE(Integer.valueOf(0), "房产"),
        MOTOR(Integer.valueOf(1), "机动车");

        public final Integer code;
        public final String desc;

        private MortgageTypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.MortgageTypeEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.MortgageTypeEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.MortgageTypeEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum FileOperateTypeEnum {
        ADD(Integer.valueOf(0), "新增"),
        MODIFY(Integer.valueOf(1), "修改"),
        REMOVE(Integer.valueOf(2), "删除");

        public final Integer code;
        public final String desc;

        private FileOperateTypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.FileOperateTypeEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.FileOperateTypeEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.FileOperateTypeEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum FileClassTwoEnum {
        PRIVATE_ID(Integer.valueOf(0), "本人身份信息"),
        PRIVATE_DATA(Integer.valueOf(1), "个人资料信息"),
        BOOKLET(Integer.valueOf(2), "户口信息"),
        MARRIAGE(Integer.valueOf(3), "婚姻信息"),
        HOME_MEMBERS(Integer.valueOf(4), "家庭成员信息"),
        WORK(Integer.valueOf(5), "工作信息"),
        HOUSE(Integer.valueOf(6), "房产信息"),
        VEHICLE(Integer.valueOf(7), "机动车信息"),
        COMPANY_BASIC(Integer.valueOf(8), "企业基本信息"),
        COMPANY_OPERATE(Integer.valueOf(9), "企业经营信息"),
        MORTGAGE(Integer.valueOf(10), "抵质押信息"),
        LEGAL(Integer.valueOf(11), "法人信息"),
        SENIOR_MANAGER(Integer.valueOf(12), "高管信息"),
        TAX(Integer.valueOf(13), "纳税信息"),
        OTHER(Integer.valueOf(14), "其他资料");

        public final Integer code;
        public final String desc;

        private FileClassTwoEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.FileClassTwoEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.FileClassTwoEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.FileClassTwoEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum FileClassOneEnum {
        USER(Integer.valueOf(0), "用户资料"),
        LOAN(Integer.valueOf(1), "贷款订单资料");

        public final Integer code;
        public final String desc;

        private FileClassOneEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static FileEnums.FileClassOneEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.FileClassOneEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.FileClassOneEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }

    public static enum FileTypeEnum {
        SELF_ID_CARD_FRONT(100100, "身份证-正面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.PRIVATE_ID),
        SELF_ID_CARD_REVERSE(100101, "身份证-反面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.PRIVATE_ID),
        BOOKLET(100200, "本人户口页", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.BOOKLET),
        BOOKLET_FRONT_PAGE(100201, "户口本首页", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.BOOKLET),
        MARRIAGE_CERTIFICATE(100300, "结婚证", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.MARRIAGE),
        SINGLE_STATUS_CERTIFICATE(100301, "单身证明", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.MARRIAGE),
        DIVORCE_CERTIFICATE(100302, "离婚证明", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.MARRIAGE),
        HOME_ID_CARD_FRONT(100400, "家庭成员身份证-正面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.HOME_MEMBERS),
        HOME_ID_CARD_REVERSE(100401, "家庭成员身份证-反面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.HOME_MEMBERS),
        BUSINESS_LICENSE(100500, "营业执照", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.COMPANY_BASIC),
        AGENCY_CREDIT_CODE(100501, "机构信用代码证", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.COMPANY_BASIC),
        OPEN_ACCOUNT_PERMISSION(100502, "开户许可证", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.COMPANY_BASIC),
        QUALIFICATION_CERTIFICATE(100503, "资质整数", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.COMPANY_BASIC),
        COMPANY_ARTICLES(100504, "公司章程", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.COMPANY_BASIC),
        LEGAL_ID_CARD_FRONT(100600, "法人身份证-正面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.LEGAL),
        LEGAL_ID_CARD_REVERSE(100601, "法人身份证-反面", FileEnums.FileClassOneEnum.USER, FileEnums.FileClassTwoEnum.LEGAL),
        PRIVATE_HOUSE(200100, "房本复印件", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.HOUSE),
        PRIVATE_VEHICLE(200101, "车本复印件", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.VEHICLE),
        PRIVATE_BANK_WATER(200102, "个人银行流水", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.PRIVATE_DATA),
        PRIVATE_OTHER(200103, "其他财产证明", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.PRIVATE_DATA),
        MORTGAGE_BELONGING(200200, "抵质押物权归属证明", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.MORTGAGE),
        MORTGAGE_OTHER(200201, "他项权利证明", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.MORTGAGE),
        MORTGAGE_GUARANTEE(200202, "抵押物照片", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.MORTGAGE),
        RENT_CONTRACT(200300, "租赁合同", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.COMPANY_OPERATE),
        PURCHASING_SELLING_CONTRACT(200301, "产品购销合同", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.COMPANY_OPERATE),
        SALE_CERTIFICATE(200302, "销售证明", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.COMPANY_OPERATE),
        ENTERPRISE_BANK_WATER(200303, "企业银行流水", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.COMPANY_OPERATE),
        TAX_CERTIFICATE(200400, "纳税证明", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        TAX_APPLICATION(200401, "纳税申请", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        FINANCIAL_STATEMENT(200402, "财务报表", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        GROUP_HEAD_STATEMENT(200403, "集团本部报表", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        GROUP_MERGE_STATEMENT(200404, "集团合并报表", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        CAPITAL_VERIFICATION_REPORT(200405, "验资报告", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        BOARD_MEMBERS(200406, "董事会成员名单", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        COMPANY_CHARGE_MEMBERS(200407, "公司负责人成员名单", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        FINANCIAL_CHARGE_MEMBERS(200408, "财务负责人成员名单", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        BOARD_LENDING_DECISIONS(200409, "董事会贷款决议", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        SHAREHOLDERS_LENDING_DECISIONS(200410, "股东会贷款决议", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.TAX),
        GOV_FINANCING_APPROVAL(200500, "政府类融资项目批文", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.OTHER),
        GOV_FINANCING_LICENCE(200501, "政府类融资项目许可证", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.OTHER),
        GOV_FINANCING_FEASIBILITY_REPORT(200502, "政府类融资项目可研报告", FileEnums.FileClassOneEnum.LOAN, FileEnums.FileClassTwoEnum.OTHER);

        public final Integer code;
        public final String desc;
        public final FileEnums.FileClassOneEnum classOneEnum;
        public final FileEnums.FileClassTwoEnum classTwoEnum;

        private FileTypeEnum(Integer code, String desc, FileEnums.FileClassOneEnum classOneEnum, FileEnums.FileClassTwoEnum classTwoEnum) {
            this.code = code;
            this.desc = desc;
            this.classOneEnum = classOneEnum;
            this.classTwoEnum = classTwoEnum;
        }

        public static FileEnums.FileTypeEnum instance(Integer code) {
            if (code == null) {
                return null;
            } else {
                FileEnums.FileTypeEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    FileEnums.FileTypeEnum e = var1[var3];
                    if (e.code.equals(code)) {
                        return e;
                    }
                }

                return null;
            }
        }
    }
}
