package com.mahi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mahi.model.Question;
import com.mahi.model.QuestionForm;
import com.mahi.model.Result;
import com.mahi.repository.QuestionRepo;
import com.mahi.repository.ResultRepo;

@Service
public class QuizService {

    @Autowired
    private QuestionRepo qRepo;

    @Autowired
    private ResultRepo rRepo;

    // Instead of autowiring Question/Result/QuestionForm as beans,
    // we create them inside methods (those are entities/DTOs, not services)

    public QuestionForm getQuestions() {
        List<Question> allQues = qRepo.findAll();
        List<Question> qList = new ArrayList<>();
        Random random = new Random();

        System.out.println("Total questions found in DB: " + allQues.size());

        // Select either 5 or however many questions are available
        int numberOfQuestions = Math.min(5, allQues.size());

        for (int i = 0; i < numberOfQuestions; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand); // prevent duplicates
        }

        QuestionForm qForm = new QuestionForm();
        qForm.setQuestions(qList);

        return qForm;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for (Question q : qForm.getQuestions()) {
            if (q.getAns() == q.getChose()) {
                correct++;
            }
        }
        return correct;
    }

    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUsername(result.getUsername());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        rRepo.save(saveResult);
    }

    public List<Result> getTopScore() {
        return rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
    }
}
