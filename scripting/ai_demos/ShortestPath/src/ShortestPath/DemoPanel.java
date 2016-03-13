/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author R. N. Robson
 */
public class DemoPanel
    extends JPanel {
    
    JPanel      demoAreaPanel   = null;
    JLabel      stepComment     = null;
    JTextArea   explainArea     = null;
    JTextArea   outputArea      = null;
    JPanel      topPanel        = new JPanel();
    JPanel      middlePanel     = new JPanel();
    JPanel      outputPanel     = new JPanel();
    JPanel      explainPanel    = new JPanel();
    ControlPanel controls       = null;

    public DemoPanel()
    {
        
        buildContent(true, true, true, true);
    }

    public DemoPanel(boolean useComment,
            boolean useExplanation,
            boolean useOutput,
            boolean useControls)
    {
        buildContent(useComment, useExplanation, useOutput, useControls);
    }

    protected void buildContent(boolean useComment,
            boolean useExplanation,
            boolean useOutput,
            boolean useControls)
    {
        setLayout(new BorderLayout());
        demoAreaPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(demoAreaPanel, BorderLayout.CENTER);
        middlePanel.setLayout(new BorderLayout());
        explainPanel.setLayout(new BorderLayout());
        outputPanel.setLayout(new BorderLayout());

        explainPanel.setBorder(BorderFactory.createTitledBorder("Explanation"));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        if(useComment)
        {
            stepComment = new JLabel();
            stepComment.setText("---");
            topPanel.add(stepComment, BorderLayout.SOUTH);
        }
        if(useExplanation)
        {
            explainArea = new JTextArea(2, 30);
            explainArea.setEditable(false);
            explainArea.setLineWrap(true);
            
            explainPanel.add(explainArea, BorderLayout.CENTER);
            middlePanel.add(explainPanel, BorderLayout.SOUTH);
        }
        if(useOutput)
        {
            outputArea = new JTextArea(5, 30);
            outputArea.setEditable(false);
            JScrollPane outScroll = new JScrollPane(outputArea);
            outputPanel.add(outScroll, BorderLayout.CENTER);
            middlePanel.add(outputPanel, BorderLayout.CENTER);
        }
        if(useControls)
        {
            controls = new ControlPanel(this);
            add(controls, BorderLayout.SOUTH);
        }


        if(useExplanation || useOutput)
        {
            add(topPanel, BorderLayout.NORTH);
            add(middlePanel, BorderLayout.CENTER);
        }
        else
        {
            add(topPanel, BorderLayout.CENTER);
        }
   
    }

    public JPanel getControlPanel()
    {
        return controls;
    }

    public JPanel getDemoAreaPanel()
    {
        return demoAreaPanel;
    }

    public JLabel getComment()
    {
        return stepComment;
    }

    public JTextArea getExplainArea()
    {
        return explainArea;
    }

    public JTextArea getOutputArea()
    {
        return outputArea;
    }
}
