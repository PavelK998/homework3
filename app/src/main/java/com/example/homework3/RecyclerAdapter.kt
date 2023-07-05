package com.example.homework3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.CountryItemBinding

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    val countryList = mutableListOf<CountryList>(
        CountryList(id = 1, R.drawable.russia, countryName = "Россия"),
        CountryList(id = 2, R.drawable.argentina, countryName = "Аргентина"),
        CountryList(id = 3, R.drawable.egypt, countryName = "Египт"),
        CountryList(id = 4, R.drawable.braziliya, countryName = "Бразилия"),
        CountryList(id = 5, R.drawable.finland, countryName = "Финляндия"),
        CountryList(id = 6, R.drawable.france, countryName = "Франция"),
        CountryList(id = 7, R.drawable.germany, countryName = "Германия" ),
        CountryList(id = 8, R.drawable.gonduras, countryName = "Гондурас"),
        CountryList(id = 9, R.drawable.gruziya, countryName = "Грузия"),
        CountryList(id = 10, R.drawable.indiya, countryName = "Индия"),
        CountryList(id = 11, R.drawable.islandiya, countryName = "Исландия"),
        CountryList(id = 12, R.drawable.italy, countryName = "Италия"),
        CountryList(id = 13, R.drawable.katar, countryName = "Катар"),
        CountryList(id = 14, R.drawable.marocco, countryName = "Марокко"),
        CountryList(id = 15, R.drawable.oae, countryName = "ОАЭ"),
        CountryList(id = 16, R.drawable.moldaviya, countryName = "Молдавия"),
        CountryList(id = 17, R.drawable.palau, countryName = "Палау"),
        CountryList(id = 18, R.drawable.senegal, countryName = "Синегал"),
        CountryList(id = 19, R.drawable.shveciya, countryName = "Швеция"),
        CountryList(id = 20, R.drawable.shveycariya, countryName = "Швейцария"),
        CountryList(id = 21, R.drawable.slovakiya, countryName = "Словакия"),
        CountryList(id = 22, R.drawable.tanzaniya, countryName = "Танзания"),
        CountryList(id = 23, R.drawable.tuvalu, countryName = "Тувалу"),
        CountryList(id = 24, R.drawable.urugvay, countryName = "Уругвай"),
        CountryList(id = 25, R.drawable.venesuela, countryName = "Венесуэда"),
        CountryList(id = 26, R.drawable.usa, countryName = "США"),
        CountryList(id = 27, R.drawable.vyetnam, countryName = "Вьетнам"),
        CountryList(id = 28, R.drawable.yapan, countryName = "Япония"),
        CountryList(id = 29, R.drawable.velikobritaniya, countryName = "Великобритания"),
        CountryList(id = 30, R.drawable.uzbekistan, countryName = "Узбекистан")
    )

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = CountryItemBinding.bind(item)
        fun bind(country: CountryList) = with(binding) {
            ivFlag.setImageResource(country.flagID)
            tvId.text = country.id.toString()
            tvName.text = country.countryName

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(countryList[position])
    }


    override fun getItemCount(): Int {
        return countryList.size
    }


}

