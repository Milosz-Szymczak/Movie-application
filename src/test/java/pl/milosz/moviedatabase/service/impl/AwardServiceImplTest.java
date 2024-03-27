package pl.milosz.moviedatabase.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.entity.Award;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.exception.AwardNotFoundException;
import pl.milosz.moviedatabase.repository.AwardRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceImplTest {

    @Mock
    private AwardRepository awardRepository;

    @InjectMocks
    private AwardServiceImpl awardService;

    @Test
    void getAwards_should_FindAllAwards_and_ReturnAwardList() {
        //given
        Movie movie = Movie.builder().movieId(1L).title("test").build();
        Award award = Award.builder().awardName("test").movie(movie).build();
        List<Award> list = List.of(award);

        //when
        when(awardRepository.findAll()).thenReturn(list);
        List<AwardDto> awardsList = awardService.getAwards(movie);

        //then
        Assertions.assertThat(awardsList).isNotNull();
        Assertions.assertThat(awardsList.size()).isEqualTo(1);

        verify(awardRepository).findAll();

    }

    @Test
    void saveAward_should_SaveAward() {
        Movie movie = Movie.builder().movieId(1L).title("test").build();
        AwardDto award = AwardDto.builder().awardName("test").movie(movie).build();

        when(awardRepository.save(any(Award.class))).thenReturn(new Award());

        awardService.saveAward(award);

        verify(awardRepository).save(any(Award.class));

    }

    @Test
    void deleteAward_should_deleteAward() {
        // Given
        Long awardId = 1L;
        Award award = new Award();
        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        // When
        awardService.deleteAward(awardId);

        // Then
        verify(awardRepository, times(1)).findById(awardId);
        verify(awardRepository, times(1)).delete(award);
    }

    @Test
    void updateAward_should_updateAward() {
        // Given
        Long awardId = 1L;
        String updatedAwardName = "Updated Award Name";
        Award award = new Award();
        when(awardRepository.findById(awardId)).thenReturn(Optional.of(award));

        // When
        awardService.updateAward(awardId, updatedAwardName);

        // Then
        verify(awardRepository, times(1)).findById(awardId);
        verify(awardRepository, times(1)).save(award);
    }

    @Test
    void updateAward_should_ThrowException_WhenNotFoundAward() {
        // Given
        Long awardId = 1L;
        String updatedAwardName = "Updated Award Name";
        when(awardRepository.findById(awardId)).thenReturn(Optional.empty());
        // When, Then
        assertThrows(AwardNotFoundException.class, () -> awardService.updateAward(awardId, updatedAwardName));
        verify(awardRepository, times(1)).findById(awardId);
        verify(awardRepository, never()).save(any());
    }
}