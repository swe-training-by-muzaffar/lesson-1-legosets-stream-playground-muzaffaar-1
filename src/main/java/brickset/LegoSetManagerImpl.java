package brickset;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

public class LegoSetManagerImpl implements LegoSetInterface{
    public static void main(String[] args) {
        var manager = new LegoSetManagerImpl();
//        System.out.println(manager.getLegoSetByThemeThenBySubtheme());
//        System.out.println(manager.getAvgPiecesOfIcons());
//        manager.printAllThemesByTag();
//        System.out.println(manager.getSumOfPiecesByTheme());
        System.out.println(manager.getSummaryStatisticsOfPiecesByTheme("Harry Potter"));
    }
    /**
     * @return list of 1000 legosets
     */
    @Override
    public List<LegoSet> getLegoSets() {
        return LegoSetInterface.super.getLegoSets();
    }

    /**
     * Prints ascending order sorted themes where its tag has "Astronomy". Hint: There are tags that may be null
     */
    @Override
    public void printAllThemesByTag() {
        getLegoSets().stream()
                .filter(legoSet -> legoSet.tags() != null && legoSet.tags().contains("Astronomy"))
                .map(LegoSet::theme)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Gets statistics according to Theme
     *
     * @param theme is given
     * @return summary statistics by theme
     */
    @Override
    public LongSummaryStatistics getSummaryStatisticsOfPiecesByTheme(String theme) {
        return getLegoSets().stream()
                .filter(legoSet -> Objects.equals(legoSet.theme(), theme))
                .mapToLong(LegoSet::pieces)
                .summaryStatistics();
    }

    /**
     * @return an average pieces by a theme "Icons"
     */
    @Override
    public Double getAvgPiecesOfIcons() {
        return getLegoSets().stream()
                .filter(legoSet -> Objects.equals(legoSet.theme(), "Icons"))
                .mapToInt(LegoSet::pieces)
                .average().getAsDouble();
    }

    /**
     * Gets sum of pieces by theme
     *
     * @return Map of String(theme) and Integer(sum pieces)
     */
    @Override
    public Map<String, Integer> getSumOfPiecesByTheme() {
        return getLegoSets().stream()
                .collect(
                        Collectors.groupingBy(
                                LegoSet::theme,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        legoSets -> legoSets.stream().mapToInt(LegoSet::pieces).sum()
                                )
                        )
                );
    }

    /**
     * @return Map of themes that mapped to sub themes that itself mapped to LegoSet
     */
    @Override
    public Map<String, Map<String, Set<LegoSet>>> getLegoSetByThemeThenBySubtheme() {
        return getLegoSets().stream()
                .filter(legoSet -> legoSet.theme() != null && legoSet.subtheme() != null)
                .collect(
                        Collectors.groupingBy(
                                LegoSet::theme,
                                Collectors.groupingBy(
                                        LegoSet::subtheme,
                                        Collectors.toSet()
                                )
                        )
                );
    }
}
