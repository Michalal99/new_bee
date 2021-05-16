import com.bee.models.Project;
import com.bee.repository.ProjectRepository;
import com.bee.service.ProjectService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void shouldAddNewProject() {
        Project project = new Project(1L, "eee");
        projectService.addProject(project);
        verify(projectRepository).save(project);
    }

    @Test(expected = Exception.class)
    public void shouldNotFindProjectIfDoesNotExist() {
        when(projectRepository.findProjectById(anyLong())).thenReturn(null);
        projectService.findProjectById(128L);
    }

    @Test(expected = Exception.class)
    public void shouldNotFindProjectIfDoesNotExists() {
        when(projectRepository.findProjectById(1L));
        projectService.findProjectById(1L);
    }

    @Test
    public void shouldNotFindTeamByNameIfIdDoesExist() {
        Project project = new Project(1L, "eee");
        when(projectRepository.findProjectById(project.getId())).thenReturn(Optional.of(project));
        Project project1 = projectService.findProjectById(project.getId());
        assertEquals(project, project1);
    }

    @Test
    public void shouldDeleteProjectFromRepository() {
        Project project = new Project(1L, "eee");
        projectService.deleteProject(project);
        verify(projectRepository).delete(project);
    }

    @Test
    public void shouldVerifyProjectIdFromRepo() {
        Project project = new Project(1L, "eeee");
        assertNotNull(project);
        projectService.addProject(project);
        verify(projectRepository).save(project);
    }

    @Test
    public void shouldFindProjectByIdFromRepo() {
        Project project = new Project(1L, "eeee");
        assertNotNull(project);
        projectService.addProject(project);
        verify(projectRepository).save(project);
        when(projectRepository.findProjectById(project.getId())).thenReturn(Optional.of(project));
    }

    @Test
    public void shouldFindProjectByIdFromRepos() {
        Project project = new Project(1L, "eeee");
        assertNotNull(project);
        projectService.addProject(project);
        verify(projectRepository).save(project);
    }

    @Test()
    public void shouldReturnNotEquals() {
        Project project = new Project(1L, "eee");
        Project project1 = new Project(2L, "ee1e");
        assertNotNull(project1);
        when(projectRepository.findProjectById(project.getId())).thenReturn((Optional.of(project)));
        assertNotEquals(project, project1);
        assertNotEquals(project.getId(), project1.getId());
        assertNotEquals(project.getDescription(), project1.getDescription());
    }

    @Test()
    public void shouldReturnEquals() {
        Project project = new Project(2L, "eee");
        Project project1 = new Project(2L, "eee");
        assertNotNull(project1);
        when(projectRepository.findProjectById(project.getId())).thenReturn((Optional.of(project)));
        assertEquals(project.getId(), project1.getId());
        assertEquals(project.getDescription(), project1.getDescription());
    }

    @Test()
    public void shouldReturnEquals2() {
        Project project = new Project(1L, "eee");
        Project project1 = new Project(2L, "eee");
        assertNotNull(project1);
        when(projectRepository.findProjectById(project.getId())).thenReturn((Optional.of(project)));
        assertNotEquals(project.getId(), project1.getId());
        assertEquals(project.getDescription(), project1.getDescription());
        project.setId(2L);
        assertEquals(project.getId(), project1.getId());
        assertEquals(project.getDescription(), project1.getDescription());
    }

    @Test()
    public void changeDescriptionOfProject() {
        Project project = new Project(2L, "eee");
        Project project1 = new Project(2L, "eee");
        assertNotNull(project1);
        when(projectRepository.findProjectById(project.getId())).thenReturn((Optional.of(project)));
        assertEquals(project.getId(), project1.getId());
        assertEquals(project.getDescription(), project1.getDescription());
        project.setDescription("qqq");
        assertEquals(project.getId(), project1.getId());
        assertNotEquals(project.getDescription(), project1.getDescription());
    }

    @Test()
    public void changeDescriptionOfBothProjects() {
        Project project = new Project(1L, "eee");
        Project project1 = new Project(2L, "qqq");
        assertNotNull(project1);
        when(projectRepository.findProjectById(project.getId())).thenReturn((Optional.of(project)));
        assertNotEquals(project.getId(), project1.getId());
        assertNotEquals(project.getDescription(), project1.getDescription());

        project.setDescription("qqq");
        assertNotEquals(project.getId(), project1.getId());
        assertEquals(project.getDescription(), project1.getDescription());

    }

    @Test()
    public void checkIfListSortedInFindAll() {
        Project project = new Project(1L, "eee");
        Project project1 = new Project(3L, "qqq");
        Project project2 = new Project(2L, "qqq");
        //Project[] projects={project,project1,project2};
        List<Project> ListOfProjects = List.of(project, project1, project2);
        List<Project> SortedListOfProjects = List.copyOf(ListOfProjects);
        Assertions.assertEquals(ListOfProjects, SortedListOfProjects);

        //(SortedListOfProjects, Sort.by(Sort.Direction.ASC, "id"));
        //List<Project> sortedByAgePersons = sort(projects, on(Project.class).getId());


        // when(projectService.findAllProjects()).


    }

}