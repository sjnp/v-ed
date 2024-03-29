package com.ved.backend.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CommentStateProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.ReportStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.Answer;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Category;
import com.ved.backend.model.Chapter;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentState;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Post;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.ReasonReport;
import com.ved.backend.model.ReportState;
import com.ved.backend.model.Review;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CommentRepo;
import com.ved.backend.repo.CommentReportRepo;
import com.ved.backend.repo.CommentStateRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.repo.PostReportRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.repo.ReportStateRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.repo.ReviewReportRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.request.ReviewRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MockDatabase {
    
    @Autowired private AppRoleRepo appRoleRepo;
    @Autowired private AppUserRepo appUserRepo;
    @Autowired private StudentRepo studentRepo;
    @Autowired private InstructorRepo instructorRepo;
    @Autowired private CourseStateRepo courseStateRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private CourseRepo courseRepo;
    @Autowired private PublishedCourseRepo publishedCourseRepo;
    @Autowired private StudentCourseRepo studentCourseRepo;
    @Autowired private AnswerRepo answerRepo;
    @Autowired private PostRepo postRepo;
    @Autowired private CommentStateRepo commentStateRepo;
    @Autowired private CommentRepo commentRepo;
    @Autowired private ReviewRepo reviewRepo;
    @Autowired private ReasonReportRepo reasonReportRepo;
    @Autowired private ReportStateRepo reportStateRepo;
    @Autowired private ReviewReportRepo reviewReportRepo;
    @Autowired private PostReportRepo postReportRepo;
    @Autowired private CommentReportRepo commentReportRepo;

    @Autowired private RoleProperties roleProperties;
    @Autowired private CourseStateProperties courseStateProperties;
    @Autowired private CategoryProperties categoryProperties;
    @Autowired private CommentStateProperties commentStateProperties;
    @Autowired private ReportStateProperties reportStateProperties;

    @Autowired private MockMvc mockMvc;

    private String usernameStudent = "student@test.com";
    private String passwordStudent = "Password123";

    private String usernameInstructor = "instructor@test.com";
    private String passwordInstructor = "Password123";

    public void clear() {
        commentReportRepo.deleteAll();
        postReportRepo.deleteAll();
        reviewReportRepo.deleteAll();
        reportStateRepo.deleteAll();
        reasonReportRepo.deleteAll();
        reviewRepo.deleteAll();
        commentRepo.deleteAll();
        commentStateRepo.deleteAll();
        postRepo.deleteAll();
        answerRepo.deleteAll();
        studentCourseRepo.deleteAll();
        publishedCourseRepo.deleteAll();
        courseRepo.deleteAll();
        categoryRepo.deleteAll();
        courseStateRepo.deleteAll();
        instructorRepo.deleteAll();
        studentRepo.deleteAll();
        appUserRepo.deleteAll();
        appRoleRepo.deleteAll();
    }
    
    public void mock_app_role() {
        List<String> roles = Arrays.asList(
            roleProperties.getAdmin(),
            roleProperties.getStudent(),
            roleProperties.getInstructor()
        );

        for(String role : roles) {
            AppRole appRole = AppRole.builder().name(role).build();
            appRoleRepo.save(appRole);
        }
    }

    public void mock_student() {
        AppRole studentRole = appRoleRepo.findByName("STUDENT");
        Collection<AppRole> appRoles = new ArrayList<>();
        appRoles.add(studentRole);

        AppUser appUser = AppUser.builder()
            .username("student@test.com")
            .password("password")
            .appRoles(appRoles)
            .build();

        Student student = Student.builder()
            .firstName("StudentFirstname")
            .lastName("StudentLastName")
            .biography("Biography student")
            .occupation("Occupation student")
            .profilePicUri("profile_student.jpg")
            .appUser(appUser)
            .build();
        
        appUser.setStudent(student);

        appUserRepo.save(appUser);
        studentRepo.save(student);
    }

    public void mock_instructor() {
        AppRole studentRole = appRoleRepo.findByName("STUDENT");
        AppRole instructoRole = appRoleRepo.findByName("INSTRUCTOR");
        Collection<AppRole> appRoles = new ArrayList<>();
        appRoles.add(studentRole);
        appRoles.add(instructoRole);

        AppUser appUser = AppUser.builder()
            .username("instructor@test.com")
            .password("password")
            .appRoles(appRoles)
            .build();

        Student student = Student.builder()
            .firstName("InstructorFirstname")
            .lastName("InstructorLastName")
            .biography("Biography instructor")
            .occupation("Occupation instructor")
            .profilePicUri("profile_instructor.jpg")
            .appUser(appUser)
            .build();

        Instructor instructor = Instructor.builder()
            .student(student)
            .recipientId("recipient_id")
            .build();

        appUser.setStudent(student);
        student.setInstructor(instructor);

        appUserRepo.save(appUser);
        studentRepo.save(student);
        instructorRepo.save(instructor);
    }

    public void mock_course_state() {
        List<String> names = Arrays.asList(
            courseStateProperties.getIncomplete(),
            courseStateProperties.getPending(),
            courseStateProperties.getApproved(),
            courseStateProperties.getRejected(),
            courseStateProperties.getPublished()
        );

        for(String name : names) {
            CourseState courseState = CourseState.builder().name(name).build();
            courseStateRepo.save(courseState);
        }
    }

    public void mock_category() {
        List<String> names = Arrays.asList(
            categoryProperties.getAcademic(),
            categoryProperties.getArt(),
            categoryProperties.getBusiness(),
            categoryProperties.getDesign(),
            categoryProperties.getProgramming()
        );

        for(String name : names) {
            Category category = Category.builder().name(name).build();
            categoryRepo.save(category);
        }
    }

    public void mock_course(Long price, String courseStateName, String categoryName) {
        AppUser appUser = appUserRepo.findByUsername("instructor@test.com");
        Student student = studentRepo.findById(appUser.getStudent().getId()).get();
        Instructor instructor = student.getInstructor();

        Chapter chapter = new Chapter();
        HashMap<String, String> assignment = new HashMap<>();
        assignment.put("assignment 1", "assingment 1 ?");
        assignment.put("assignment 2", "assingment 2 ?");
        assignment.put("assignment 3", "assingment 3 ?");
        List<HashMap<String, String>> assignemnts = new ArrayList<>();
        assignemnts.add(assignment);
        HashMap<String, Object> section = new HashMap<>();
        section.put("section 1", "section 1");
        section.put("section 2", "section 2");
        section.put("section 3", "section 3");
        List<HashMap<String, Object>> sections = new ArrayList<>();
        sections.add(section);
        chapter.setName("chapter 1");
        chapter.setAssignments(assignemnts);
        chapter.setSections(sections);
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);

        CourseState courseState = courseStateRepo.findByName(courseStateName);

        Category category = categoryRepo.findByName(categoryName).get();

        Course course = Course.builder()
            .instructor(instructor)
            .name("course " + category.getName())
            .overview("overview course " + category.getName())
            .requirement("requirement course " + category.getName())
            .price(price)
            .chapters(chapters)
            .pictureUrl("course_picture.jpg")
            .category(category)
            .courseState(courseState)
            .build();

        PublishedCourse publishedCourse = PublishedCourse.builder()
            .course(course)
            .star(0D)
            .totalScore(0D)
            .totalUser(0L)
            .build();

        instructor.getCourses().add(course);
        course.setPublishedCourse(publishedCourse);

        courseRepo.save(course);
        instructorRepo.save(instructor);
    }

    public void mock_student_course(Student student, Course course) {
        StudentCourse studentCourse = StudentCourse.builder()
            .student(student)
            .course(course)
            .paySuccess(true)
            .build();
        studentCourseRepo.save(studentCourse);
    }

    public AppUser getAppUserRoleStudent() {
        List<AppRole> appRoles = new ArrayList<>();
        Student student = Student.builder()
            .firstName("Firstname")
            .lastName("Lastname")
            .biography("Biography")
            .occupation("Occupation")
            .build();
        AppUser appUser = AppUser.builder()
            .username(this.usernameStudent)
            .password(this.passwordStudent)
            .appRoles(appRoles)
            .student(student)
            .build();
        return appUser;
    }

    public void mock_register_student() throws Exception {
        AppUser appUser = getAppUserRoleStudent();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(appUser);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users/new-student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
    }

    public void mock_register_instructor() throws Exception {
        AppUser appUser = getAppUserRoleStudent();
        appUser.setUsername(this.usernameInstructor);
        appUser.setPassword(this.passwordInstructor);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(appUser);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users/new-student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
        AppUser user = appUserRepo.findByUsername("instructor@test.com");
        AppRole appRole = appRoleRepo.findByName(roleProperties.getInstructor());
        user.getAppRoles().add(appRole);
        Student student = user.getStudent();
        Instructor instructor = Instructor.builder()
            .recipientId("recipient-id")
            .student(student)
            .build();
        student.setInstructor(instructor);
        studentRepo.save(student);
        appUserRepo.save(user);
    }

    private ResultActions mock_login(Map<String, String> requestLogin) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestLogin);
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );
        return resultActions;
    }

    public ResultActions mock_login_student() throws Exception {
        Map<String, String> request = Map.of(
            "username", this.usernameStudent, 
            "password", this.passwordStudent
        );
        return this.mock_login(request);
    }

    public ResultActions mock_login_instructor() throws Exception {
        Map<String, String> request = Map.of(
            "username", this.usernameInstructor, 
            "password", this.passwordInstructor
        );
        return this.mock_login(request);
    } 

    public String getCredential(ResultActions resultActions, String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String json = response.getContentAsString();
        @SuppressWarnings("unchecked")
        Map<String, Object> credentials = objectMapper.readValue(json, Map.class);
        return credentials.get(name).toString();
    }

    public void mock_answer(Long courseId, int chapterIndex, int noIndex, String fileName) {
        Course course = courseRepo.findById(courseId).get();
        Student student = appUserRepo.findAppUserByUsername(usernameStudent).get().getStudent();
        StudentCourse studentCourse = studentCourseRepo.findByStudentAndCourse(student, course).get();
        
        Answer answer = Answer.builder()
            .chapterIndex(chapterIndex)
            .noIndex(noIndex)
            .fileName(fileName)
            .datetime(LocalDateTime.now())
            .studentCourse(studentCourse)
            .build();
        answerRepo.save(answer);
    }

    public void mock_comment_state() {
        List<String> commentStateNames = Arrays.asList(
            commentStateProperties.getOwner(),
            commentStateProperties.getStudent(),
            commentStateProperties.getInstructor()
        );

        for(String commentStateName : commentStateNames) {
            CommentState commentState = CommentState.builder().name(commentStateName).build();
            commentStateRepo.save(commentState);
        }
    }

    public void mock_post(Long courseId, String topic, String detail) {
        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findById(courseId).get();
        StudentCourse studentCourse = studentCourseRepo.findByStudentAndCourse(student, course).get();
        Post post = Post.builder()
            .course(studentCourse.getCourse())
            .studentCourse(studentCourse)
            .topic(topic)
            .detail(detail)
            .createDateTime(LocalDateTime.now())
            .visible(true)
            .build();
        postRepo.save(post);
    }

    public void mock_published_course(Long courseId) {
        Course course = courseRepo.findById(courseId).get();
        PublishedCourse publishedCourse = PublishedCourse.builder()
            .course(course)
            .star(0.0)
            .totalScore(0.0)
            .totalUser(0L)
            .build();
        publishedCourseRepo.save(publishedCourse);
    }

    public void mock_create_review(Long courseId, Double rating, String commentReview) {
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(rating)
            .review(commentReview)
            .build();

        Course course = courseRepo.findById(courseId).get();
        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        StudentCourse studentCourse = studentCourseRepo.findByStudentAndCourse(student, course).get();

        PublishedCourse publishedCourse = studentCourse.getCourse().getPublishedCourse();
        Double newTotalScore = publishedCourse.getTotalScore() + reviewRequest.getRating();
        Long newTotalUser = publishedCourse.getTotalUser() + 1;
        Double newStar = newTotalScore / newTotalUser;
        publishedCourse.setTotalScore(newTotalScore);
        publishedCourse.setTotalUser(newTotalUser);
        publishedCourse.setStar(newStar);
        publishedCourseRepo.save(publishedCourse);

        Review review = Review.builder()
            .rating(reviewRequest.getRating())
            .comment(reviewRequest.getReview())
            .reviewDateTime(LocalDateTime.now())
            .visible(true)
            .student(studentCourse.getStudent())
            .publishedCourse(publishedCourse)
            .build();
        reviewRepo.save(review);
    }

    public void mock_reason_report() {
        List<String> descriptions = Arrays.asList(
            "ข้อความสแปม",
            "ละเมิดสิทธิของฉัน",
            "ข้อความเท็จ ไม่ตรงกับข้อเท็จจริง",
            "ใช้ถ้อยคำที่รุนแรง หยาบคาย",
            "เนื้อหารุนแรง และน่ารังเกียจ"
        );

        for(String description : descriptions) {
            ReasonReport reasonReport = ReasonReport.builder()
                .description(description)
                .build();
            reasonReportRepo.save(reasonReport);
        }
    }

    public void mock_report_state() {
        List<String> reportStateNames = Arrays.asList(
            reportStateProperties.getApproved(),
            reportStateProperties.getPending(),
            reportStateProperties.getRejected()
        );
        
        for (String name : reportStateNames) {
            ReportState reportState = ReportState.builder()
                .name(name)
                .build();
            reportStateRepo.save(reportState);
        }
    }

    public void mock_comment(Long postId, String commentText) {
        Post post = postRepo.findById(postId).get();
        CommentState commentState = commentStateRepo.findByName(commentStateProperties.getOwner());
        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Comment comment = Comment.builder()
            .post(post)
            .comment(commentText)
            .commentDateTime(LocalDateTime.now())
            .visible(true)
            .commentState(commentState)
            .student(student)
            .build();
        post.getComments().add(comment);
        postRepo.save(post);
    }

}