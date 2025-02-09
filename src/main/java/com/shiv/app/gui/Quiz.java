package com.shiv.app.gui;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.UIManager;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import lombok.Getter;
import lombok.Setter;

import com.shiv.app.util.CustomHTMLEditorKit;
import com.shiv.app.util.OrderProvider;

import com.shiv.app.model.Question;
import com.shiv.app.AppConfig;
import com.shiv.app.dao.QuestionsProvider;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz {

    @Getter
    private JFrame frame;
    @Getter
    private Integer score;
    @Getter
    private Integer counter;
    private JButton next;
    private JButton prev;
    private JButton submit;

    private JMenuBar menuBar;
    private JPanel statusPanel;

    private JPanel centerPanel;
    private JPanel controlPanel;
    private JLabel scoreLabel;
    private JLabel counterLabel;
    private QuizComponent quizComponent;
    private QuestionListComponent questionListComponent;

    public Quiz() {
        frame = new JFrame();
        menuBar = new JMenuBar();
        score = 0;
        counter = 0;
        scoreLabel = new JLabel("Score: " + score.toString());
        counterLabel = new JLabel("Current Question: " + counter.toString());
        quizComponent = new QuizComponent();
        questionListComponent = new QuestionListComponent();
        initComponents();
    }

    public void initMenuBar(){
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
    }

    public void initStatusPanel(){
        statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout());
        statusPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
        statusPanel.add(scoreLabel);
        statusPanel.add(new JLabel("/"));
        statusPanel.add(counterLabel);
    }

    public void initCenterPanel(){
        questionListComponent.setActionListener(new QuestionSelectListener());

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.3;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(questionListComponent.getContainer(), c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(quizComponent.getPanel(), c);
    }

    public void initControlPanel(){
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        next = new JButton("Next");
        prev = new JButton("Prev");
        buttonPanel.add(prev);
        buttonPanel.add(next);
        submit = new JButton("Submit");
        next.addActionListener(new NextButtonListener());
        prev.addActionListener(new PreviousButtonListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.3;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        controlPanel.add(submit, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        controlPanel.add(buttonPanel, c);
    }

    public void initComponents() {
        initMenuBar();
        initStatusPanel();
        initCenterPanel();
        initControlPanel();
        frame.setJMenuBar(menuBar);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.NORTH, statusPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, controlPanel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setQuestion(1);
    }

    class QuestionSelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("hi this is listener!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            JRadioButton selectedRadioButton = (JRadioButton) event.getSource();
            Integer questionNumber = Integer.parseInt(selectedRadioButton.getText());
            setQuestion(questionNumber);
        }
    }

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (counter + 1 <= AppConfig.getAppConfig().getTotalQuestions()) {
                counter++;
                setQuestion(counter);
                questionListComponent.setQuestion(counter);
            }
        }
    }

    class PreviousButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (counter - 1 > 0) {
                counter--;
                setQuestion(counter);
                questionListComponent.setQuestion(counter);
            }
        }
    }

    public void setQuestion(Integer questionNumber) {
        counter = questionNumber;
        counterLabel.setText("Current Question: " + counter.toString());
        quizComponent.setQuestion(questionNumber);
    }
}