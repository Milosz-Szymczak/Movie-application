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
import pl.milosz.moviedatabase.repository.AwardRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}