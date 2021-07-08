package checks.readability;


import common.BaseCheck;
import common.CheckerAnnotation;
import common.DefectMessage;
import parser.ObjectiveCParser;

/**
 * @author camelxiao
 * @version V2.0
 * @date 2020/4/8
 */
@CheckerAnnotation
public class MissingDocInterface extends BaseCheck
{
    private String source;

    @Override
    public void exitClassImplementation(ObjectiveCParser.ClassImplementationContext ctx)
    {
        if (!getFilePath().endsWith(".h")){
            System.out.println("do not check file in MissingDocInterface: " + getFilePath());
            return;
        }

        int docLastLine = ctx.start.getLine()-2;
        if(docLastLine <= 0)
        {
            DefectMessage msg = new DefectMessage();
            msg.setLineNo(ctx.start.getLine());
            msg.setCheckerName(this.getClass().getSimpleName());
            msg.setFilePath(getFilePath());
            msg.setMessage("接口缺少必须的注释");
            this.addMessage(msg);
        }
        else
        {
            String docLastLineString = source.split("\n")[docLastLine];
            if(!docLastLineString.contains("//") && !docLastLineString.contains("/*") && !docLastLineString.contains("*/"))
            {
                DefectMessage msg = new DefectMessage();
                msg.setLineNo(ctx.start.getLine());
                msg.setCheckerName(this.getClass().getSimpleName());
                msg.setFilePath(getFilePath());
                msg.setMessage("接口缺少必须的注释");
                this.addMessage(msg);
            }
        }
    }

    @Override
    public void checkText(String source)
    {
        this.source = source;
    }
}